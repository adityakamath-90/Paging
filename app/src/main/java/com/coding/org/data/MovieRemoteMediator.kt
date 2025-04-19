package com.coding.org.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.coding.org.data.local.TmdbDatabase
import com.coding.org.data.local.model.MovieEntity
import com.coding.org.data.local.model.RemoteKey
import com.coding.org.data.remote.TmdbService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val service: TmdbService,
    private val database: TmdbDatabase
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        Log.d("Mediator", "LoadType = $loadType")

        val page = when (loadType) {
            LoadType.REFRESH -> 1

            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                val remoteKey = lastItem?.let {
                    database.remoteKeyDao().remoteKeyByMovie(it.movieId)
                }

                if (remoteKey?.nextKey == null) {
                    Log.d("Mediator", "No next key. End of pagination.")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                remoteKey.nextKey
            }

            LoadType.PREPEND -> {
                // Ignoring PREPEND as paging starts from the beginning
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        return try {
            // Move API key to BuildConfig or config file
            val response = service.popularMovie(
                page = page,
                api_key = "6e5e29145fb574f21d8efc11b07761e0"
            )

            val movies = response.results

            Log.d("Mediator", "Fetched ${movies.size} movies from API on page $page")

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    Log.d("Mediator", "Clearing database on refresh")
                    database.remoteKeyDao().clearRemoteKeys()
                    database.movieDao().clearAll()
                }

                val remoteKeys = movies.map { movie ->
                    RemoteKey(
                        movieId = movie.id,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (page >= response.total_pages) null else page + 1
                    )
                }

                database.movieDao().insertAll(movies.map { it.toDataEntity() })
                database.remoteKeyDao().insertAll(remoteKeys)
            }

            MediatorResult.Success(endOfPaginationReached = page >= response.total_pages)
        } catch (e: IOException) {
            Log.e("Mediator", "Network error", e)
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            Log.e("Mediator", "HTTP error", e)
            MediatorResult.Error(e)
        } catch (e: Exception) {
            Log.e("Mediator", "Unexpected error", e)
            MediatorResult.Error(e)
        }
        }
}