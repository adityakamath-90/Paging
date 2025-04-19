package com.coding.org.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coding.org.data.local.model.RemoteKey

@Dao
interface RemoteKeyDao {
    @Query("SELECT * FROM remote_keys WHERE movieId = :id")
    suspend fun remoteKeyByMovie(id: String): RemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<RemoteKey>)

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}

