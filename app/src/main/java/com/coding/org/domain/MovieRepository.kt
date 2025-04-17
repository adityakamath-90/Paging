package com.coding.org.domain

import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(pageNo : Int, pageLimit : Int ? = 20) : Flow<Movie>
}