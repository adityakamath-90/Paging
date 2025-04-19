package com.coding.org.data

import com.coding.org.data.local.model.MovieEntity
import com.coding.org.data.remote.Movie

fun MovieEntity.toDomainModel(): com.coding.org.domain.Movie {
    return com.coding.org.domain.Movie(
        title = this.title,
        id = this.movieId,
        posterUrl = this.posterPath ?: ""
    )
}

fun Movie.toDataEntity(): MovieEntity {
    return MovieEntity(movieId = this.id, title = this.title, posterPath = this.poster_path)
}