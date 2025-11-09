package com.ojasx.eduplay.Notifications.LocalNotifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build  // ✅ CHANGE: Added Build import for version checks
import androidx.core.app.NotificationCompat
import com.ojasx.eduplay.MainActivity
import com.ojasx.eduplay.Notifications.LocalNotifications.ReminderReceiver
import com.ojasx.eduplay.R
import java.util.*

// ✅ CHANGE: Added try-catch block to prevent crashes if notification fails
fun createNotification(context: Context) {
    try {
        val notification = NotificationCompat.Builder(context, "channel_id")
            .setContentTitle("Notification title")
            .setContentText("This is an instant notification")
            .setSmallIcon(R.drawable.user)
            .setAutoCancel(true)
            .setContentIntent(createPendingIntent(context))
            .build()

        BaseApplication.notificationManager.notify(100, notification)
    } catch (e: Exception) {
        // ✅ CHANGE: Catch exceptions to prevent app crashes
        e.printStackTrace()
    }
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

// ✅ CHANGE: Changed return type from Unit to Boolean to indicate success/failure
// ✅ CHANGE: Added comprehensive error handling to prevent app crashes
fun scheduleReminder(context: Context, hour: Int, minute: Int): Boolean {
    return try {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // ✅ CHANGE: Added check for Android 12+ (API 31+) exact alarm permission
        // This is CRITICAL - without this check, the app crashes on Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                // ✅ CHANGE: Return false instead of crashing - let UI handle the error
                // Permission not granted, return false to handle in UI
                return false
            }
        }

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

        // ✅ CHANGE: Added version check and fallback for older Android versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            // ✅ CHANGE: Use deprecated method for older Android versions
            @Suppress("DEPRECATION")
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }

        // ✅ CHANGE: Return true on success
        true
    } catch (e: SecurityException) {
        // ✅ CHANGE: Catch SecurityException specifically (thrown when permission denied)
        // This was causing your app to crash!
        e.printStackTrace()
        false
    } catch (e: Exception) {
        // ✅ CHANGE: Catch any other exceptions to prevent crashes
        e.printStackTrace()
        false
    }
}