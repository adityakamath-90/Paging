package com.coding.org.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coding.org.data.local.model.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies ORDER BY movieId asc")
    fun getPagedMovies(): PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}