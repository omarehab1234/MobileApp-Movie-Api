package com.example.myapplication.ViewModel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Movie
import com.example.myapplication.models.MovieResponse
import com.example.myapplication.notification.NotificationHelper
import com.example.myapplication.receiver.ReminderScheduler
import com.example.myapplication.ui.screens.MovieDetailState
import com.example.myapplication.ui.screens.MovieState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepo: MovieRepository,
    private val notificationHelper: NotificationHelper,
    private val reminderScheduler: ReminderScheduler
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies.asStateFlow()

    private val _favMovies = MutableStateFlow<List<Movie>>(emptyList())

    private val _state = MutableStateFlow<MovieState>(MovieState.Loading)
    val movieState = _state.asStateFlow()

    val favMovies = _favMovies.asStateFlow()

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            _state.value = MovieState.Loading

            try {
                _movies.value = movieRepo.getPopularMovies().results

                _state.value = MovieState.Success(movies = _movies.value)
            }catch (e: Exception){
                _state.value = MovieState.Error(errorMessage =  e.message ?: "Something went wrong")
            }
        }
    }
    private var currentPage = 1
    private var isLoadingNextPage = false

    public fun loadNextPage(){
        viewModelScope.launch {
            val currentState = _state.value as? MovieState.Success
                ?: return@launch

            if (currentState.isLoadingMore) {
                return@launch
            }

            val currentMovies = currentState.movies

            _state.value = MovieState.Success(
                movies = currentMovies,
                isLoadingMore = true
            )

            try {
                currentPage++
                val newMovies = movieRepo.getPopularMovies(currentPage ).results
                _state.value = MovieState.Success(movies=currentMovies + newMovies,
                                                    isLoadingMore = false)

            } catch (e: Exception) {
                _state.value = MovieState.Success(
                    movies = currentMovies,
                    isLoadingMore = false)

            }
        }
    }
    public fun getFavoriteMovies(){
        viewModelScope.launch {
            _favMovies.value = movieRepo.getAllFavMovies()
        }
    }
    public fun addMovie(movie: Movie){
        val updatedMovie = movie.copy(fav = true)
        viewModelScope.launch {
            movieRepo.addMovie(updatedMovie)
            _stateDetail.value = MovieDetailState.Success(updatedMovie)
            notificationHelper.showFavoriteNotification(updatedMovie)
        }
    }
    public fun deleteMovie(movie: Movie){
        val updatedMovie = movie.copy(fav = false)
        viewModelScope.launch {
            movieRepo.deleteMovie(movie)
            _stateDetail.value = MovieDetailState.Success(updatedMovie)
            notificationHelper.showDelNotification(updatedMovie)
        }
    }

    private val _movie = MutableStateFlow<Movie?>(null)
    val movie = _movie.asStateFlow()

    private val _stateDetail = MutableStateFlow<MovieDetailState>(MovieDetailState.Loading)
    val stateDetail = _stateDetail.asStateFlow()

    public fun getMovie(id: Int) {
        viewModelScope.launch {

            val dbMovie = movieRepo.getMovie(id)

            if (dbMovie != null) {
                _stateDetail.value = MovieDetailState.Success(dbMovie)
                return@launch
            }

            val apiMovie: Movie = movieRepo.getMovieApi( id)
            if(apiMovie != null) {
                _stateDetail.value = MovieDetailState.Success(apiMovie)
            }
        }
    }

    public fun scheduleMovie(movie: Movie,time:String){
        try {
            reminderScheduler.schedule(movie,time)
            notificationHelper.showScheduledNotification(movie,time)
        }catch (e: Exception){
//            we will do event Later to looks cool
        }
    }

}