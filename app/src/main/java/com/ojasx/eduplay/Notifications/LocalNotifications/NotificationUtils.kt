package com.ojasx.eduplay.Notifications.LocalNotifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.ojasx.eduplay.MainActivity
import com.ojasx.eduplay.Notifications.LocalNotifications.ReminderReceiver
import com.ojasx.eduplay.R
import java.util.*

fun createNotification(context: Context) {
    val notification = NotificationCompat.Builder(context, "channel_id")
        .setContentTitle("Notification title")
        .setContentText("This is an instant notification")
        .setSmallIcon(R.drawable.user)
        .setAutoCancel(true)
        .setContentIntent(createPendingIntent(context))
        .build()

    BaseApplication.notificationManager.notify(100, notification)
}

fun createPendingIntent(context: Context): PendingIntent {
    val intent = Intent(context, MainActivity::class.java)
    return PendingIntent.getActivity(
        context,
        100,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )
}

fun scheduleReminder(context: Context, hour: Int, minute: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, ReminderReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        if (before(Calendar.getInstance())) {
            add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}
