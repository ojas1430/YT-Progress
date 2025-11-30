package com.ojasx.eduplay.ui.BottomBar.Screens.SettingsScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ojasx.eduplay.R
import com.ojasx.eduplay.ui.theme.cardcolor
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavController
import com.ojasx.eduplay.ViewModel.ProfileViewModel
import com.ojasx.eduplay.ui.profile.profile

@Composable
fun SettingsScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel
    ) {

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
        SettingsItem("Set daily/weekly goals")
        SettingsItem("Reset progress")
        SettingsItem("Export progress data")

        // Notifications
        SettingsSectionTitle("Notifications")
        SettingsItem("Reminders for watch/revise videos"){
            navController.navigate("ShowLocalNotificationScreen")
        }
        SettingsItem("Daily streak reminder")

        // Playback & Experience
        SettingsSectionTitle("Playback & Experience")
        SettingsItem("Default playback speed")
        SettingsItem("Auto-mark video as completed")
        SettingsItem("Theme: Light / Dark")

        // Data & Backup
        SettingsSectionTitle("Data & Backup")
        SettingsItem("Sync with cloud")
        SettingsItem("Clear cache")
        SettingsItem("Backup / Restore")

        // Help & Support
        SettingsSectionTitle("Help & Support")
        SettingsItem("FAQs")
        SettingsItem("Contact developer")
        SettingsItem("Rate this app")
        SettingsItem("Report a bug"){
            navController.navigate("ReportABugMainScreen")
        }

        // About
        SettingsSectionTitle("About")
        SettingsItem("App version 1.0.0")
        SettingsItem("Privacy Policy")
        SettingsItem("Terms & Conditions")

        Spacer(Modifier.height(100.dp))
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
