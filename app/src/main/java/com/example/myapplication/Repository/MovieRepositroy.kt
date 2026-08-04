package com.example.myapplication.Repository
import com.example.myapplication.Api.RetrofitInstance
import com.example.myapplication.DB.MovieDao
import javax.inject.Inject
import  com.example.myapplication.Api.ApiKeyy
import com.example.myapplication.Api.ApiService
import com.example.myapplication.models.Movie

class MovieRepositroy @Inject constructor(
    private val movieDao : MovieDao,
    private val api : ApiService
){
    suspend fun getPopularMovies() = api.getPopularMovies(ApiKeyy.api_key)

    suspend fun addFavMovie(movie: Movie) = movieDao.insertMovie(movie)

    suspend fun getAllFavMovies() = movieDao.getAllMovies()
}