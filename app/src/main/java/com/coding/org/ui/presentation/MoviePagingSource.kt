package com.coding.org.ui.presentation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.coding.org.domain.GetMoviesUseCase
import com.coding.org.domain.Movie
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class MoviePagingSource @Inject constructor(private val useCase: GetMoviesUseCase) :
    PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition)
        return page?.prevKey ?: page?.nextKey
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = useCase.getMoviesList(page)
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