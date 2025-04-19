package com.coding.org.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbService {
    @GET("movie/popular")
    suspend fun popularMovie(
        @Query("page") page: Int,
        @Query("api_key") api_key: String
    ): PopularMovie
}