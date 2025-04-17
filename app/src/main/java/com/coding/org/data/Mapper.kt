package com.coding.org.data

import com.coding.org.data.remote.Movie

fun Movie.toDomainModel() : com.coding.org.domain.Movie {
    return com.coding.org.domain.Movie(title = this.title, id = this.id)
}

fun Movie.toDataModel() : Movie {
    return Movie(
        title = this.title,
        adult = this.adult,
        backdrop_path = this.backdrop_path,
        genre_ids = this.genre_ids,
        id = this.id,
        original_language = this.original_language,
        original_title = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        poster_path = this.poster_path,
        release_date = this.release_date,
        video = this.video,
        vote_average = this.vote_average,
        vote_count = this.vote_count
    )
}


fun com.coding.org.domain.Movie.toDataModel() : Movie {
    return Movie(
        title = this.title,
        adult = false,
        backdrop_path = "",
        genre_ids = listOf(),
        id = this.id,
        original_language = "",
        original_title = "this.original_title",
        overview = "this.overview",
        popularity = 1.0f,
        poster_path = "this.poster_path",
        release_date = "this.release_date",
        video = false,
        vote_average = 1.0f,
        vote_count = 1
    )
}
