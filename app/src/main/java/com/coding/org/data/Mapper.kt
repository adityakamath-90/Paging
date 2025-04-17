package com.coding.org.data

import com.coding.org.data.remote.Movie

fun Movie.toDomainModel() : com.coding.org.domain.Movie {
    return com.coding.org.domain.Movie(title = this.title, id = this.id)
}