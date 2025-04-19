package com.coding.org.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
     fun getMovies(pageNo: Int, pageLimit: Int? = 20): Flow<PagingData<Movie>>
}