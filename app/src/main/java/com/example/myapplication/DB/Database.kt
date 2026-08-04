package com.example.myapplication.DB
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.models.Movie

@Database(
    entities = [Movie::class],
    version = 3
)

abstract class Database:RoomDatabase(){
    abstract fun movieDao():MovieDao
}