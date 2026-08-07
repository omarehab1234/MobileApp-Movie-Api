package com.example.myapplication.widget

import androidx.glance.appwidget.GlanceAppWidgetReceiver

class MovieWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: MovieWidget = MovieWidget()
}
