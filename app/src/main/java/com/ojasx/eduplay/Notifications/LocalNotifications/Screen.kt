package com.ojasx.eduplay.Notifications.LocalNotifications

import android.Manifest
import android.app.TimePickerDialog
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.*

@Preview
@Composable
fun ShowNotificationScreen() {
    val context = LocalContext.current
    val notificationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    // Request permission once
    LaunchedEffect(Unit) {
        notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Button(onClick = { createNotification(context) }) {
                Text("Show Notification Now")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val now = Calendar.getInstance()
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        scheduleReminder(context, hour, minute)
                    },
                    now.get(Calendar.HOUR_OF_DAY),
                    now.get(Calendar.MINUTE),
                    true
                ).show()
            }) {
                Text("Set Reminder")
            }
        }
    }
}
