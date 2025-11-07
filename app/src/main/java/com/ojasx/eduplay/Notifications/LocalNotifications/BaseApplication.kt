package com.ojasx.eduplay.Notifications.LocalNotifications

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class BaseApplication : Application() {

    companion object {
        lateinit var notificationManager: NotificationManager
    }

    override fun onCreate() {
        super.onCreate()

        notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Create channel (for Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_id",
                "Reminders",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.enableVibration(true)
            channel.enableLights(true)
            notificationManager.createNotificationChannel(channel)
        }
    }
}