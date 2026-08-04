package com.example.myapplication.DB

import androidx.room.*
import com.example.myapplication.models.Movie

@Dao
interface MovieDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: Movie)


    @Delete
    suspend fun deleteMovie(movie: Movie)


    @Update
    suspend fun updateMovie(movie: Movie)


    @Query("select * from movies")
    suspend fun getAllMovies(): List<Movie>

//    @Query("Select 1 from movies where  id = movie.id ")
//    suspend fun getExitMovie(movie: Movie){
//
//    }
}