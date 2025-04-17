package com.coding.org.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.coding.org.domain.Movie
import com.coding.org.domain.MovieRepository

class MoviePagingSource(private val repository: MovieRepository) : PagingSource<Int,Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        Log.i("MOVIES","getRefreshKey ")
        return state.pages.lastIndex
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = repository.getMovies(page)
            Log.i("MOVIES","MoviePagingSoirce "+response)
            LoadResult.Page(
                data = response.toList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.toList().isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}