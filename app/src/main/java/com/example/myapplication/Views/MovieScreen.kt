package com.example.myapplication.Views
import com.example.myapplication.Api.ApiKeyy
import com.example.myapplication.models.MovieResponse
import com.example.myapplication.Api.RetrofitInstance
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


class MovieScreen{

    companion object {
        @Composable
        fun movieScreen(movies: MovieResponse, navController: NavController) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {

                Button(
                    onClick = {
                        navController.popBackStack() // Go back to previous screen
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("<")
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    movies?.let {
                        items(it.results) { movie ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                                    .clickable{
                                        navController
                                            .currentBackStackEntry?
                                            .savedStateHandle

                                        navController.navigate("movieDetail")
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
            }
        }

        @Composable
        fun movieDetails(navController: NavController){
            val movie = navController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<Movie>("movie")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .verticalScroll(rememberScrollState())
            ) {

                // Back button
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("<")
                }


                // Movie poster
                if (movie != null) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                        contentDescription = movie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp),
                        contentScale = ContentScale.Crop
                    )


                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {

                        // Title
                        Text(
                            text = movie.title,
                            color = Color.White,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )


                        Spacer(modifier = Modifier.height(12.dp))


                        // Rating + Release date row
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFFFC107)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "⭐ ${movie.vote_average}",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }


                            Spacer(modifier = Modifier.width(16.dp))


                            Text(
                                text = movie.release_date,
                                color = Color.LightGray,
                                fontSize = 16.sp
                            )
                        }


                        Spacer(modifier = Modifier.height(25.dp))


                        // Overview title
                        Text(
                            text = "Overview",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )


                        Spacer(modifier = Modifier.height(8.dp))


                        // Description
                        Text(
                            text = movie.overview,
                            color = Color.LightGray,
                            fontSize = 16.sp,
                            lineHeight = 24.sp
                        )
                    }
                }
            }
        }

    }








}
