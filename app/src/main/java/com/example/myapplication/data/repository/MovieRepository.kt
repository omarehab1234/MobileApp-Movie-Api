package com.example.myapplication.data.repository

import com.example.myapplication.Api.ApiKeyy
import com.example.myapplication.Api.ApiService
import com.example.myapplication.data.db.MovieDao
import com.example.myapplication.models.Movie
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieDao : MovieDao,
    private val api : ApiService
){
    suspend fun getPopularMovies(pageId: Int = 1) = api.getPopularMovies(
                                                        padeId = pageId,
                                                        apiKey = ApiKeyy.api_key)

    suspend fun addFavMovie(movie: Movie) = movieDao.insertMovie(movie)

    suspend fun getAllFavMovies() = movieDao.getAllMovies()

    suspend fun addMovie(movie: Movie) = movieDao.insertMovie(movie)

    suspend fun deleteMovie(movie: Movie) = movieDao.deleteMovie(movie)

    suspend fun  getMovie(movieId: Int) = movieDao.getMovie(movieId)

    suspend fun  getMovieApi(movieId: Int): Movie = api.getMovieDetails(movieId, ApiKeyy.api_key)

}