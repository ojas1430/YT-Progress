package com.ojasx.eduplay.ui.BottomBar.Screens.PlayListScreen.Stats

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun StatsDialog(
    totalVideos: Int,
    completedVideos: Int,
    pinnedVideos: Int,
    revisionVideos: Int,
    onDismiss: () -> Unit
) {
    val progress = if (totalVideos == 0) 0f
    else completedVideos.toFloat() / totalVideos

    val remaining = totalVideos - completedVideos

    val animatedProgress by animateFloatAsState(progress)

    val purple = Color(0xFF7B61FF)

    val message = when {
        progress == 1f -> "🔥 Perfect! You finished everything"
        progress > 0.7f -> "🚀 Almost there, keep going!"
        progress > 0.3f -> "💪 Good progress, stay consistent"
        else -> "📌 Start small, build momentum"
    }

    Dialog(onDismissRequest = { onDismiss() }) {

        // 🔥 Background dim
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.4f))
                .clickable { onDismiss() },
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .shadow(12.dp, RoundedCornerShape(20.dp))
                    .background(Color.White, RoundedCornerShape(20.dp))
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Your Stats",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                //  Circular Progress with glow
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .shadow(
                            elevation = 12.dp,
                            shape = CircleShape,
                            ambientColor = purple,
                            spotColor = purple
                        )
                ) {
                    CircularProgressIndicator(
                        progress = { animatedProgress },
                        strokeWidth = 10.dp,
                        color = purple,
                        trackColor = Color.LightGray.copy(0.3f),
                        modifier = Modifier.size(130.dp)
                    )

                    Text(
                        text = "${(progress * 100).toInt()}%",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "$completedVideos / $totalVideos completed",
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                //  Motivation line
                Text(
                    text = message,
                    color = purple,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

                //  Mini stats bars
                MiniStatBar("📌 Pinned", pinnedVideos, totalVideos, purple)
                Spacer(modifier = Modifier.height(10.dp))

                MiniStatBar("📝 Revision", revisionVideos, totalVideos, Color(0xFFFF9800))
                Spacer(modifier = Modifier.height(10.dp))

                MiniStatBar("⏳ Remaining", remaining, totalVideos, Color.Gray)
            }
        }
    }
}