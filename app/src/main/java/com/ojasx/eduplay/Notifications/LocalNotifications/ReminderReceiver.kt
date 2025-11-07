package com.ojasx.eduplay.Notifications.LocalNotifications

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.ojasx.eduplay.MainActivity
import com.ojasx.eduplay.R

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notification = NotificationCompat.Builder(context, "channel_id")
            .setContentTitle("Study Reminder")
            .setContentText("Time to continue your playlist 🎥")
            .setSmallIcon(R.drawable.user)
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, MainActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            .build()

        BaseApplication.notificationManager.notify(200, notification)
    }
}