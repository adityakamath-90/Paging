package com.coding.org.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "movies"
)
data class MovieEntity(
    @PrimaryKey
    val movieId: String,
    val posterPath: String?,
    val title: String,
)