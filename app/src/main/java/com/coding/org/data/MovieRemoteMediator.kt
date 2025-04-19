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
                val remoteKey =
                    lastItem?.let { database.remoteKeyDao().remoteKeyByMovie(it.movieId) }
                remoteKey?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = false)
            }
        }

        return try {
            val response =
                service.popularMovie(page = page, api_key = "6e5e29145fb574f21d8efc11b07761e0")
            val movies = response.results
            Log.d("Mediator", "API returned ${movies.size} movies on page $page")

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    Log.d("Mediator", " Clearing old data")
                    database.remoteKeyDao().clearRemoteKeys()
                    database.movieDao().clearAll()
                }

                Log.i("MOVIES", "Page is $page")

                val keys = movies.map {
                    RemoteKey(
                        movieId = it.id,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (page >= response.total_pages) null else page + 1
                    )
                }
                database.movieDao().insertAll(movies.map { it.toDataEntity() })
                database.remoteKeyDao().insertAll(keys)
                Log.i("Mediator", "Total ppages: ${response.total_pages}")


            }

            MediatorResult.Success(endOfPaginationReached = page >= response.total_pages)
        } catch (e: Exception) {
            Log.e("Mediator", "Exception: $e")
            MediatorResult.Error(e)
        }
    }
}