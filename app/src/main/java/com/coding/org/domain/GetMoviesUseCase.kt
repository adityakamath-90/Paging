package com.coding.org.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(val repository : MovieRepository) {
    fun getMoviesList(pageNo: Int): Flow<PagingData<Movie>> {
        return repository.getMovies(pageNo)
    }
}