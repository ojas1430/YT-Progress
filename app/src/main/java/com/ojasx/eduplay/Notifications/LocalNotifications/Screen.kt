package com.ojasx.eduplay.Notifications.LocalNotifications

import android.Manifest
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ShowLocalNotificationScreen() {
    val context = LocalContext.current
    val notificationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    // Ask for POST_NOTIFICATION permission once
    LaunchedEffect(Unit) {
        notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    var showTimePicker by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Button(onClick = { createNotification(context) }) {
                Text("Show Notification Now")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { showTimePicker = true }) {
                Text("Set Reminder")
            }

            if (selectedTime.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Reminder set for: $selectedTime")
            }

            if (showTimePicker) {
                TimePickerDialog(
                    onDismissRequest = { showTimePicker = false },
                    onConfirm = { hour, minute ->
                        selectedTime = String.format("%02d:%02d", hour, minute)
                        scheduleReminder(context, hour, minute)
                        showTimePicker = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (hour: Int, minute: Int) -> Unit
) {
    val timePickerState = rememberTimePickerState()

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(timePickerState.hour, timePickerState.minute)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        },
        text = {
            TimePicker(state = timePickerState)
        }
    )
}
