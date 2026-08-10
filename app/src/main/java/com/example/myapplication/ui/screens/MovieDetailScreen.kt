package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myapplication.ViewModel.MovieViewModel
import com.example.myapplication.models.Movie
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Arrangement

sealed interface MovieDetailState {
    object Loading : MovieDetailState

    data class Success(
        val movie: Movie,
        ): MovieDetailState

    data class Error(
        val message: String
    ):MovieDetailState

}
@Composable
fun MovieDetailScreen (
    navController: NavHostController,
    movieId: Int,
    viewModel: MovieViewModel = hiltViewModel()
){
    LaunchedEffect(movieId) {
        viewModel.getMovie(movieId)
    }
    val movieState by viewModel.stateDetail.collectAsState()
    val listState = rememberLazyListState()

    val imageHeight = (450 - listState.firstVisibleItemScrollOffset / 4)
        .coerceAtLeast(0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

            when (val state = movieState) {
                MovieDetailState.Loading -> loadingState()
                is MovieDetailState.Success -> {
                    val movie = state.movie
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                    movie.let { movie ->
                        item {
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                                contentDescription = movie.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(imageHeight.dp),
                                contentScale = ContentScale.Crop
                            )
                        }

                        item {
                            Column(
                                modifier = Modifier.padding(20.dp)
                            ) {

                                Text(
                                    text = movie.title,
                                    color = Color.White,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(0xFFFFC107)
                                        )
                                    ) {
                                        Text(
                                            text = "⭐ ${movie.vote_average}",
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Text(
                                        text = movie.release_date,
                                        color = Color.LightGray
                                    )
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                    text = "Overview",
                                    color = Color.White,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = movie.overview,
                                    color = Color.LightGray,
                                    lineHeight = 24.sp
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    listOf("18:00", "20:00", "22:00").forEach { time ->
                                        Button(
                                            onClick = {
                                                viewModel.scheduleMovie(movie,time)
                                            }
                                        ) {
                                            Text(time)
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))

                                favButton(movie,viewModel)

                                Spacer(modifier = Modifier.height(100.dp))
                            }
                        }
                    }

                }

            }
            is MovieDetailState.Error ->{
                val message = state.message
                Text(
                    text = message
                )
            }
        }
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "<"
            )
        }
    }
}

@Composable
fun favButton (
    movie: Movie,
    viewModel: MovieViewModel){
    if(!movie.fav) {
        Button(
            onClick = {
                    viewModel.addMovie(movie)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(52.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE53935), // Red
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp
            )
        ) {
            Text(
                text = "❤️ Add to Favorites",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
    else{
        Button(
            onClick = {
                viewModel.deleteMovie(movie)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(52.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE53935), // Red
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp
            )
        ) {
            Text(
                text = "Remove from Favorites",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun loadingState() {
    CircularProgressIndicator()
}
