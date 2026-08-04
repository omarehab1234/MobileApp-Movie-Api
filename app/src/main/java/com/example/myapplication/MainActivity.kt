package com.example.myapplication

import com.example.myapplication.Api.ApiKeyy
import com.example.myapplication.models.MovieResponse
import com.example.myapplication.Api.RetrofitInstance
import com.example.myapplication.Views.MovieScreen
import com.example.myapplication.models.Movie
import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.FontScaling
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import androidx.room.Room
import com.example.myapplication.DB.Database
import com.example.myapplication.DB.MovieDao
import kotlin.jvm.java
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            Database::class.java,
            "my_database"
        ).build()
        val movieDao = db.movieDao()
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    HomeScreen(navController)
                }


                composable("moviesPopular") {
                    moviePopularScreen(navController)
                }

                composable("moviesFavorite") {
                    movieFavoriteScreen(navController,movieDao)
                }

                composable("movieDetail/{json}") { backStackEntry ->

                    val json = backStackEntry.arguments?.getString("json") ?: return@composable

                    val movie = Gson().fromJson(json, Movie::class.java)

                    MovieScreen.movieDetails(movie, navController,movieDao)
                }

                }
            }
        }
    }

    @Composable
    fun movieFavoriteScreen(navController: NavController,movieDao: MovieDao) {
        var movies:List<Movie>?  by remember { mutableStateOf(null) }

        LaunchedEffect(Unit) {
            movies = movieDao.getAllMovies()
        }

        if(movies != null){
            MovieScreen.favScreen(movies!!,navController)
        }
    }

    @Composable
    fun moviePopularScreen(navController: NavController) {

        var movies by remember {
            mutableStateOf<MovieResponse?>(null)
        }

        LaunchedEffect(Unit) {
            movies = RetrofitInstance.api.getPopularMovies(ApiKeyy.api_key)
        }
        if (movies != null) {
            MovieScreen.movieScreen(movies!!, navController)
        }
    }

    @Composable
    fun HomeScreen(navController: NavController) {
        moviePopularScreen(navController)

    }




