package com.ojasx.eduplay.ui.BottomBar.Screens.PlayListScreen.Stats

import android.graphics.drawable.Icon
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ojasx.eduplay.API.PlaylistViewModel

@Composable
fun StatsButton(
    playlistviewModel: PlaylistViewModel,
    navController: NavController
) {
    var showStats by remember { mutableStateOf(false) }

    val total by playlistviewModel.totalVideos.collectAsState()
    val completed by playlistviewModel.completedVideos.collectAsState()
    val pinned by playlistviewModel.pinnedVideos.collectAsState()
    val revision by playlistviewModel.revisionVideos.collectAsState()
    Button(
        onClick = {
            showStats = true
        },
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(
            horizontal = 10.dp,
            vertical = 2.dp
        ),
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(50)
            )
            .height(30.dp)
    ) {
        Icon(
            imageVector = Icons.Default.BarChart,
            contentDescription = "Stats",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "Stats",
            color = Color.White
        )
    }

    if (showStats) {
        StatsDialog(
            totalVideos = total,
            completedVideos = completed,
            pinnedVideos = pinned,
            revisionVideos = revision,
            onDismiss = { showStats = false }
        )
    }
}