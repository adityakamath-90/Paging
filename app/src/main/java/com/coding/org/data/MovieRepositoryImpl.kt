package com.coding.org.data

import com.coding.org.data.local.TmdbDatabase
import com.coding.org.data.remote.TmdbService
import com.coding.org.domain.Movie
import com.coding.org.domain.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: TmdbService,
    private val database: TmdbDatabase
) : MovieRepository {

    override suspend fun getMovies(pageNo: Int, pageLimit: Int?): Flow<Movie> {
        return service.popularMovie(
            pageNo,
            "6e5e29145fb574f21d8efc11b07761e0"
        ).results.map { it -> it.toDomainModel() }.asFlow()
    }
}