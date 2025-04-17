package com.coding.org.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.coding.org.domain.Movie
import com.coding.org.domain.MovieRepository
import kotlinx.coroutines.flow.toList

class MoviePagingSource(private val repository: MovieRepository) : PagingSource<Int,Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition)
        return page?.prevKey ?: page?.nextKey    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = repository.getMovies(page)
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