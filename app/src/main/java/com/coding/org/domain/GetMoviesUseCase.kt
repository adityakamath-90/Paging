package com.coding.org.domain

import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(val repository : MovieRepository) {
     suspend fun getMoviesList() : List<Movie> {
        return repository.getMovies(1)
    }
}