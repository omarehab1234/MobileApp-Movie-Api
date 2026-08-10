package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.example.myapplication.ViewModel.MovieViewModel
import com.example.myapplication.models.Movie
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect

sealed interface MovieState{
        object Loading: MovieState
        data class Success(
            val movies : List<Movie>,
            val isLoadingMore: Boolean = false
            ): MovieState
        data class Error(
            val errorMessage: String
        ): MovieState
}

@Composable
fun MovieScreenShowALl(
    navController: NavController,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val scrollState = rememberLazyListState()
    val movieState by viewModel.movieState.collectAsState()
    val fetchNextPage: Boolean by remember {
        derivedStateOf {
            val currentMovieCount = (movieState as? MovieState.Success)?.movies?.size
                ?:0
            val lastDisplayIndex = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                ?:0
            return@derivedStateOf lastDisplayIndex >= currentMovieCount - 3
        }
    }

    LaunchedEffect(fetchNextPage) {
        if(fetchNextPage) viewModel.loadNextPage()
    }
    when(val state = movieState){
        MovieState.Loading -> LoadingState()
        is MovieState.Success ->{
            val movies = state.movies
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = scrollState
            ) {
                items(movies) { movie ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                            .clickable {
                                navController.navigate("details/${movie.id}")
                            },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF1E1E1E)
                        ),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                                contentDescription = movie.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp),
                                contentScale = ContentScale.Crop
                            )

                            Text(
                                text = movie.title,
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "⭐ ${movie.vote_average}",
                                color = Color.Yellow
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Release: ${movie.release_date}",
                                color = Color.LightGray
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = movie.overview,
                                color = Color.White,
                                maxLines = 4,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
        is MovieState.Error ->{
            Text(
                text = state.errorMessage
            )
        }
    }
}

@Composable
fun LoadingState() {
    CircularProgressIndicator()
}