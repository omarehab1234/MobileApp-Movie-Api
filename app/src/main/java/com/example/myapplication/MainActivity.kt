package com.example.myapplication

import com.example.myapplication.Api.ApiKeyy
import com.example.myapplication.models.MovieResponse
import com.example.myapplication.Api.RetrofitInstance
import com.example.myapplication.models.Movie
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.myapplication.data.db.MovieDao
import dagger.hilt.android.AndroidEntryPoint
import com.example.myapplication.MovieApp
import com.example.myapplication.ui.navigation.AppNavigation

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppNavigation()
        }
    }
}





