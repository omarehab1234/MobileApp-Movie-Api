package com.example.myapplication.Api
import  com.example.myapplication.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

@GET("movie/popular")
suspend fun getPopularMovies(
    @Query("api_key") apiKey: String,
    ):MovieResponse


}