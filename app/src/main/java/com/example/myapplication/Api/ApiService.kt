package com.example.myapplication.Api
import com.example.myapplication.models.Movie
import  com.example.myapplication.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path
import retrofit2.http.Header
interface ApiService {

@GET("movie/popular")
suspend fun getPopularMovies(
    @Query("api_key") apiKey: String,
    ):MovieResponse

@GET("account/{account_id}/favorite/movies")
suspend fun getFavoriteMovies(
    @Path("account_id") accountId: Int,
    @Header("Authorization") token: String
    ):MovieResponse

@GET("movie/{movie_id}")
suspend fun getMovieDetails(
    @Path("movie_id") movieId : Int,
    @Query("api_key") apiKey: String,
): Movie
}