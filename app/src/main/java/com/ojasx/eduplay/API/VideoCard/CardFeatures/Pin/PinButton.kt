package com.ojasx.eduplay.API.VideoCard.CardFeatures.Pin

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun PinButton(
    isPin : Boolean,
    onPinChanged : (Boolean)-> Unit
) {
    var isPin by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PinStarButton(
            isPinned = isPin,
            onPinClick = {
                isPin = !isPin
            },

        )
        Text("Pin", style = MaterialTheme.typography.bodySmall, color = Color.White)
    }
}