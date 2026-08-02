package com.example.myapplication.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (val id: Int,
                  val title: String,
                  val overview: String,
                  val poster_path: String,
                  val vote_average: Double,
                  val release_date: String
):Parcelable
