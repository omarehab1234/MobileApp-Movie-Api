package com.example.myapplication.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.Repository.MovieRepositroy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.myapplication.models.Movie
import com.example.myapplication.models.MovieResponse
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepo : MovieRepositroy
): ViewModel() {

    suspend fun getPopularMovies() = movieRepo.getPopularMovies()


}