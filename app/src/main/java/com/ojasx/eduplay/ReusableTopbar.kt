package com.ojasx.eduplay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ReusableTopBar(
    title: String,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        StatusBar()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back Arrow
            IconButton(onClick = {
                navController.navigate("SettingsScreen")
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    tint = Color.White
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
