package com.example.myapplication
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screens.MoviePopularScreen

@Composable
fun MovieApp() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "popular"
    ) {

        composable("popular") {
            MoviePopularScreen(navController = navController)
        }

    }
}