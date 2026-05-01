package com.ojasx.eduplay.ui.BottomBar.Screens.SettingsScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.ojasx.eduplay.ViewModel.AuthViewModel
import com.ojasx.eduplay.ViewModel.ProfileViewModel
import com.ojasx.eduplay.ui.profile.profile
import com.ojasx.eduplay.ui.theme.cardcolor

@Composable
fun SettingsScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel,
    authViewModel: AuthViewModel
    ) {
    val context = LocalContext.current
    val futureUpdateMessage = "These features are available in future updates."
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(
                color = cardcolor
            )
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // 🔹 Profile Section
        profile(navController,profileViewModel)

        Spacer(Modifier.height(24.dp))
        Divider(color = Color.White.copy(alpha = 0.3f))

        // Progress & Learning
        SettingsSectionTitle("Progress & Learning")
        SettingsItem("Set daily/weekly goals") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }
        SettingsItem("Reset progress") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }
        SettingsItem("Export progress data") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }

        // Notifications
        SettingsSectionTitle("Notifications")
        SettingsItem("Reminders for watch/revise videos"){
            navController.navigate("ShowLocalNotificationScreen")
        }
        SettingsItem("Daily streak reminder") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }

        // Playback & Experience
        SettingsSectionTitle("Playback & Experience")
        SettingsItem("Default playback speed") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }
        SettingsItem("Auto-mark video as completed") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }
        SettingsItem("Theme: Light / Dark") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }

        // Data & Backup
        SettingsSectionTitle("Data & Backup")
        SettingsItem("Sync with cloud") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }
        SettingsItem("Clear cache") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }
        SettingsItem("Backup / Restore") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }

        // Help & Support
        SettingsSectionTitle("Help & Support")
        SettingsItem("FAQs") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }
        SettingsItem("Contact developer") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }
        SettingsItem("Rate this app") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }
        SettingsItem("Report a bug") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }

        // About
        SettingsSectionTitle("About")
        SettingsItem("App version 1.0.0") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }
        SettingsItem("Privacy Policy") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }
        SettingsItem("Terms & Conditions") {
            Toast.makeText(context, futureUpdateMessage, Toast.LENGTH_SHORT).show()
        }

        // Account
        SettingsSectionTitle("Account")
        SettingsItem("Logout") {
            showLogoutDialog = true
        }

        Spacer(Modifier.height(100.dp))
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = {
                Text(
                    text = "Confirm Logout",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to log out?",
                    color = Color.Black.copy(alpha = 0.8f)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        authViewModel.signOut()
                        navController.navigate("LoginScreen") {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showLogoutDialog = false }) {
                    Text("No")
                }
            },
            containerColor = Color.White
        )
    }
}

@Composable
fun SettingsSectionTitle(title: String) {
    Spacer(Modifier.height(20.dp))
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
        modifier = Modifier.padding(vertical = 6.dp)
    )
}

@Composable
fun SettingsItem(
    title: String ,
    onClick:()->Unit = {}
) {
    Card(
        onClick = {onClick()},
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, color = Color.White, fontSize = 15.sp)
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Go",
                tint = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}
