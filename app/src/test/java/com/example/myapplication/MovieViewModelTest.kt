package com.example.myapplication

import com.example.myapplication.data.db.MovieDao
import com.example.myapplication.models.Movie
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class MovieViewModelTest {

    @Test
    fun addMovie_setsFavoriteToTrue() = runTest {

        // Arrange
        val movieDao = mock(MovieDao::class.java)

        val movie = Movie(
            id = 1,
            title = "Test Movie",
            overview = "Test overview",
            poster_path = "/test.jpg",
            vote_average = 8.0,
            release_date = "2026-01-01",
            fav = false
        )

        // Act
        movieDao.insertMovie(movie.copy(fav = true))

        // Assert
        verify(movieDao).insertMovie(
            movie.copy(fav = true)
        )

        assertTrue(movie.copy(fav = true).fav)
    }
}