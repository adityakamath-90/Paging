package com.coding.org.ui.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.coding.org.data.remote.MoviePagingSource
import com.coding.org.domain.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(val useCase: GetMoviesUseCase): ViewModel(){
    private val _movies = MutableStateFlow<PagingData<com.coding.org.domain.Movie>>(PagingData.empty())
    val movies = _movies.asStateFlow()

     fun getMovieList() {
        viewModelScope.launch {

            val movie = useCase.getMoviesList()

            Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = true
                ),
                pagingSourceFactory = { MoviePagingSource(useCase.repository) }
            ).flow.cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    Log.i("MOVIES", "PagingData collected")
                    _movies.value = pagingData
                }
        }
    }
}