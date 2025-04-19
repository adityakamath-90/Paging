package com.coding.org.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.coding.org.domain.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(useCase: GetMoviesUseCase) : ViewModel() {
    val movies = useCase.getMoviesList(1).cachedIn(viewModelScope)
}


