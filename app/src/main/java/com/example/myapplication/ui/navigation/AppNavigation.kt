package com.example.myapplication.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ViewModel.MovieViewModel
import com.example.myapplication.models.Movie
import com.example.myapplication.ui.screens.MovieDetailScreen
import com.example.myapplication.ui.screens.MovieFavoriteScreen
import com.example.myapplication.ui.screens.MoviePopularScreen
import com.google.gson.Gson

@Composable
fun AppNavigation(
    viewModel: MovieViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "popular"
    ) {
        composable("popular") {
            MoviePopularScreen(navController)
        }

        composable("favorite") {
            MovieFavoriteScreen(navController)
        }

        composable( route = "details/{movieId}",
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0

            MovieDetailScreen(
                navController = navController,
                movieId = movieId
            )
        }
    }
}
