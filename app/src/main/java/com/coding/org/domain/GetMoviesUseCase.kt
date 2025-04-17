package com.coding.org.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(val repository : MovieRepository) {
     suspend fun getMoviesList() : Flow<Movie> {
        return repository.getMovies(1)
    }
}