package com.example.myapplication

import com.example.myapplication.Api.ApiKeyy
import com.example.myapplication.models.MovieResponse
import com.example.myapplication.Api.RetrofitInstance
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                Greeting("Omar")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {

    var movies by remember {
        mutableStateOf<MovieResponse?>(null)
    }
    LaunchedEffect(Unit){
        movies = RetrofitInstance.api.getPopularMovies(ApiKeyy.api_key)
    }

    var count by remember {
        mutableStateOf(0)
    }

    LazyColumn(
        modifier = Modifier
            .background(Color.Black)
            .width(500.dp)
            .height(400.dp)
    ) {
        if (movies != null) {

            for (movie in movies!!.results) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {

//                Image(
//                    painter = painterResource(id = R.drawable.background),
//                    contentDescription = null
//                )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Hello $name!",
                                color = Color.White,
                                modifier = Modifier.padding(16.dp)
                            )

                            Button(
                                onClick = { count++ }
                            ) {
                                Text("Clicked $count times")
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Omar")
    }
}