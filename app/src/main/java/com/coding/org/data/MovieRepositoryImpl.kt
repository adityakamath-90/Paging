package com.coding.org.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.coding.org.data.local.TmdbDatabase
import com.coding.org.data.remote.TmdbService
import com.coding.org.domain.Movie
import com.coding.org.domain.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImpl @Inject constructor(
    private val service: TmdbService,
    private val database: TmdbDatabase
) : MovieRepository {

    override fun getMovies(pageNo: Int, pageLimit: Int?): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                prefetchDistance = 5
            ),
            remoteMediator = MovieRemoteMediator(service, database),
            pagingSourceFactory = { database.movieDao().getPagedMovies() }
        ).flow.map { pagingData ->
            pagingData.map { it ->
                it.toDomainModel()
            }
        }
    }
}