package com.coding.org.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.coding.org.data.local.dao.MovieDao
import com.coding.org.data.local.dao.RemoteKeyDao
import com.coding.org.data.local.model.MovieEntity
import com.coding.org.data.local.model.RemoteKey

@Database(
    entities = [
        MovieEntity::class,
        RemoteKey::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TmdbDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
    abstract fun remoteKeyDao() : RemoteKeyDao
}