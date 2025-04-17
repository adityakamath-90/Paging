package com.coding.org.domain

interface MovieRepository {
    suspend fun getMovies(pageNo : Int, pageLimit : Int ? = 20) : List<Movie>
}