package com.example.myapplication.models

data class MovieResponse(val page: Int,
                         var results: List<Movie>,
                         val total_pages: Int,
                         val total_results: Int){

}