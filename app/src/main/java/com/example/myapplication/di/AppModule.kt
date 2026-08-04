package com.example.myapplication.di

import com.example.myapplication.Api.ApiService
import com.example.myapplication.Api.RetrofitInstance
import com.example.myapplication.DB.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.Context
import androidx.room.Room
import com.example.myapplication.DB.MovieDao
import com.example.myapplication.Repository.MovieRepositroy
import kotlin.jvm.java
@Module
@InstallIn(SingletonComponent::class)
object AppModule{
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideMovieAPi(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDbMovie(
        @ApplicationContext context: Context
    ): Database{
        return Room.databaseBuilder(
            context,
            Database::class.java,
            "movie_db"
        ).build()
    }

    @Provides
    fun providMovieDao(database: Database): MovieDao{
        return database.movieDao()
    }

    @Provides
    @Singleton
    fun providMovieRepo(
        api: ApiService,
        movieDao: MovieDao): MovieRepositroy{
        return MovieRepositroy(movieDao,api)
    }

}