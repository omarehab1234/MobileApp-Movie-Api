package com.example.myapplication.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.myapplication.R
import com.example.myapplication.models.Movie
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val notificationChannel: NotificationChannel
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        notificationManager.createNotificationChannel(notificationChannel)
    }

    fun showFavoriteNotification(movie: Movie) {
        val notification = NotificationCompat.Builder(context, notificationChannel.id)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(movie.title)
            .setContentText("Added To Favorite")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            movie.id,
            notification
        )
    }

    fun showDelNotification(movie: Movie) {
        val notification = NotificationCompat.Builder(context, notificationChannel.id)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(movie.title)
            .setContentText("Deleted From Favorite")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            movie.id,
            notification
        )
    }

    fun showScheduledNotification(movie: Movie,time :String){
        val notification = NotificationCompat.Builder(context, notificationChannel.id)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(movie.title)
            .setContentText("The movie Has been Schedule At $time")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            movie.id,
            notification
        )
    }

    fun notifyMovie(movieId: Int, movieTitle: String){
        if (movieId== -1) return
        val notification = NotificationCompat.Builder(context, notificationChannel.id)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(movieTitle)
            .setContentText("Time to watch!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            movieId,
            notification
        )
    }

}
