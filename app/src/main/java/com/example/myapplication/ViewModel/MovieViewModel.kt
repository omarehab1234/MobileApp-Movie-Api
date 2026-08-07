package com.example.myapplication.ViewModel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Movie
import com.example.myapplication.models.MovieResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepo: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<MovieResponse?>(null)
    val movies = _movies.asStateFlow()

    private val _favMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favMovies = _favMovies.asStateFlow()

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            _movies.value = movieRepo.getPopularMovies()
        }
    }
    public fun getFavoriteMovies(){
        viewModelScope.launch {
            _favMovies.value = movieRepo.getAllFavMovies()
        }
    }
    public fun addMovie(movie: Movie){
        movie.fav = true
        viewModelScope.launch {
            movieRepo.addMovie(movie)
        }
    }
    public fun deleteMovie(movie: Movie){
        viewModelScope.launch {
            movieRepo.deleteMovie(movie)
        }
    }

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie = _movie.asStateFlow()

    public fun getMovie(id: Int) {
        viewModelScope.launch {

            val dbMovie = movieRepo.getMovie(id)

            if (dbMovie != null) {
                _movie.value = dbMovie
                return@launch
            }

            val apiMovie: Movie = movieRepo.getMovieApi( id)
            if(apiMovie != null) {
                _movie.value = apiMovie
            }
        }
    }


}