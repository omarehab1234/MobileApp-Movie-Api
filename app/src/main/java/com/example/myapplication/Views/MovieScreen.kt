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
import androidx.compose.foundation.clickable
import com.example.myapplication.models.Movie
import com.google.gson.Gson
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.Row
import android.net.Uri
import android.view.CollapsibleActionView
import androidx.compose.foundation.lazy.rememberLazyListState
import coil.compose.AsyncImage
import com.example.myapplication.DB.MovieDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.material3.ButtonDefaults
class MovieScreen{

    companion object {
        @Composable
        fun movieScreen(movies: MovieResponse, navController: NavController) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {

                Text(
                    text = "\n"
                )
                Button(onClick = {
                    navController.navigate("moviesFavorite")
                },

                    modifier = Modifier.padding(16.dp))
                {Text(
                    text = "Your Favorite movie"
                ) }
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    movies?.let {
                        items(it.results) { movie ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                                    .clickable {
                                        val json = Uri.encode(Gson().toJson(movie))
                                        navController.navigate("movieDetail/$json")
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
            }
        }

        @Composable
        fun movieDetails(
            movie: Movie,
            navController: NavController,
            movieDao: MovieDao
        ) {
            val listState = rememberLazyListState()

            val imageHeight = (450 - listState.firstVisibleItemScrollOffset / 4)
                .coerceAtLeast(0)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {

                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {

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

                            favButton(movie, movieDao)

                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }
                }

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("<")
                }
            }
        }

        @Composable
        fun favButton(movie: Movie , movieDao: MovieDao){
            if(!movie.fav) {
                Button(
                    onClick = {
                        movie.fav = true
                        CoroutineScope(Dispatchers.IO).launch {
                            movieDao.insertMovie(movie)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
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
                        CoroutineScope(Dispatchers.IO).launch {
                            movieDao.deleteMovie(movie)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
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
        fun favScreen(movies: List<Movie>, navController: NavController) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {

                Text(
                    text = "\n"
                )

                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("<")
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    movies?.let {
                        items(movies) { movie ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                                    .clickable {
                                        val json = Uri.encode(Gson().toJson(movie))
                                        navController.navigate("movieDetail/$json")
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
            }
        }



    }








}
