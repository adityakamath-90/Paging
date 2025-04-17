package com.coding.org.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.coding.org.domain.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(val useCase: GetMoviesUseCase): ViewModel(){
    val movies = Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = true
                ),
        pagingSourceFactory = { MoviePagingSource(useCase) }
            ).flow.cachedIn(viewModelScope)
    }
