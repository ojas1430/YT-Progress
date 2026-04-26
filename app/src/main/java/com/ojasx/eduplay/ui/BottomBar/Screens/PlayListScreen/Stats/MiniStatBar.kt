package com.ojasx.eduplay.ui.BottomBar.Screens.PlayListScreen.Stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // ✅ CORRECT IMPORT
import androidx.compose.ui.unit.dp

@Composable
fun MiniStatBar(
    label: String,
    value: Int,
    total: Int,
    color: Color
) {
    val progress = if (total == 0) 0f else value.toFloat() / total

    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, color = Color(0xFF666666))
            Text("$value", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(4.dp))

        LinearProgressIndicator(
            progress = progress,
            color = color,
            trackColor = Color(0xFFE0E0E0),
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
    }
}