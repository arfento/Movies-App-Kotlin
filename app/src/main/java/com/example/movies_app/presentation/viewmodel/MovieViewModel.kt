package com.example.movies_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies_app.domain.entities.Movie
import com.example.movies_app.domain.usecase.GetMoviesUseCase
import com.example.movies_app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieusecase: GetMoviesUseCase
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading)
    val stateFlow: StateFlow<Resource<List<Movie>>>
        get() = _stateFlow

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            getMovieusecase.invoke().collect() {
                _stateFlow.tryEmit(it)
            }
        }
    }

}