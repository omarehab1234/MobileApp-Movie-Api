package com.example.myapplication.receiver

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.myapplication.models.Movie
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import android.app.AlarmManager
import java.util.Calendar

class ReminderScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    fun schedule(movie: Movie,time: String){
        val intent = Intent(
            context,
            MovieBroadReceiver::class.java
        ).apply {
            action = "com.example.myapplication.MOVIE_REMINDER"
            putExtra("MOVIE_ID",movie.id)
            putExtra("MOVIE_TITLE", movie.title)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            movie.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )
        val (hour, minute) = time.split(":").map { it.toInt() }

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        val reminderTime = calendar.timeInMillis
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP
            ,reminderTime
            ,pendingIntent)
    }
}