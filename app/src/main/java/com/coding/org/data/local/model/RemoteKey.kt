package com.coding.org.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "remote_keys"
)
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    val movieId: String,
    val nextKey: Int?,
    val prevKey: Int?
)
