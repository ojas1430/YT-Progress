package com.ojasx.eduplay.Notifications.LocalNotifications

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavHostController
import com.ojasx.eduplay.Notifications.LocalNotifications.createNotification
import com.ojasx.eduplay.Notifications.LocalNotifications.scheduleReminder
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import android.content.Intent
import android.provider.Settings
import android.app.AlarmManager
import android.content.Context

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowLocalNotificationScreen(navController: NavHostController) {
    val context = LocalContext.current

    // Permission launcher for POST_NOTIFICATIONS
    val notificationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Handle permission result if needed
    }

    val exactAlarmLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        // Handle result if needed
    }

    LaunchedEffect(Unit) {
        // Request notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    var showTimePicker by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showError by remember { mutableStateOf(false) }

    val cardcolor = Color(0xFF2A2A72)
    val animatedVisible = remember { MutableTransitionState(false) }

    LaunchedEffect(Unit) {
        animatedVisible.targetState = true
    }


    fun checkAndScheduleReminder(hour: Int, minute: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                errorMessage = "Exact alarm permission is required. Please grant it in settings."
                showError = true
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                }
                exactAlarmLauncher.launch(intent)
                return
            }
        }

        val success = scheduleReminder(context, hour, minute)
        if (success) {
            selectedTime = String.format("%02d:%02d", hour, minute)
            errorMessage = null
            showError = false
        } else {
            errorMessage = "Failed to schedule reminder. Please check permissions."
            showError = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Notifications",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("SettingsScreen") }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = cardcolor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            cardcolor.copy(alpha = 0.2f),
                            Color(0xFFF7F7FF)
                        )
                    )
                )
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Manage Your Notifications",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFF333333),
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
                Text(
                    text = "Control how and when you receive reminders.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray
                    ),
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                //  Added error message display
                if (showError && errorMessage != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFFFEBEE)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = errorMessage!!,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFFC62828)
                            ),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                //  Added success message display
                if (selectedTime.isNotEmpty() && !showError) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFFE8F5E9)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "✓ Reminder set for: $selectedTime",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF2E7D32)
                            ),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                // Animated appearance for cards
                AnimatedVisibility(
                    visibleState = animatedVisible,
                    enter = fadeIn(animationSpec = tween(600)) +
                            slideInVertically(initialOffsetY = { it / 4 })
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        NotificationCard(
                            title = "Show Notification Now",
                            description = "Instantly trigger a local notification for testing.",
                            buttonText = "Show Now",
                            onClick = {
                                try {
                                    createNotification(context)
                                } catch (e: Exception) {
                                    errorMessage = "Failed to show notification: ${e.message}"
                                    showError = true
                                }
                            }
                        )

                        NotificationCard(
                            title = "Set Reminder",
                            description = "Schedule a reminder notification at a specific time.",
                            buttonText = "Set Reminder",
                            onClick = { showTimePicker = true }
                        )
                    }
                }
            }

            if (showTimePicker) {
                TimePickerDialog(
                    onDismissRequest = {
                        showTimePicker = false
                        showError = false
                    },
                    onConfirm = { hour, minute ->
                        checkAndScheduleReminder(hour, minute)
                        showTimePicker = false
                    }
                )
            }
        }
    }
}

// ---- NotificationCard component ----
@Composable
fun NotificationCard(
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color(0xFF2A2A72),
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2A2A72)
                ),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = buttonText,
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge
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