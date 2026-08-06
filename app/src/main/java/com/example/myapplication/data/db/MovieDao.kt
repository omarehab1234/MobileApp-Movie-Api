package com.example.myapplication.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovie(movieId : Int): Movie?
}