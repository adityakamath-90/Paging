package com.coding.org.data.remote

data class PopularMovie(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)