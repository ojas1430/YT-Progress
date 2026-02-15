package com.ojasx.eduplay.API.VideoCard.CardFeatures.Pin

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun PinStarButton(
    isPinned:Boolean,
    onPinClick:(Boolean)->Unit
) {
    IconButton(
        onClick = {
            onPinClick(!isPinned)
        }
    ) {
        Icon(
            imageVector = if(isPinned) Icons.Filled.Star else Icons.Outlined.Star,
            contentDescription = "",
            tint = if (isPinned) Color(0xFFFFD700) else Color.Gray
        )
    }
}