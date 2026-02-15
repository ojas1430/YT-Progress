package com.ojasx.eduplay.API.VideoCard.CardFeatures

import androidx.compose.runtime.Composable
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ReviseButton(
    isRevised: Boolean,
    onToggle: (Boolean) -> Unit
) {

    val gradient = if (isRevised) {
        Brush.linearGradient(listOf(Color(0xFF43CEA2), Color(0xFF185A9D))) // green-blue gradient
    } else {
        Brush.linearGradient(listOf(Color(0xFFa770ef), Color(0xFFcf8bf3))) // purple gradient
    }

    val iconColor by animateColorAsState(
        if (isRevised) Color.White else Color.White,
        label = "iconColor"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onToggle(!isRevised)
            }
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .shadow(6.dp, RoundedCornerShape(12.dp))
                .background(brush = gradient, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isRevised) Icons.Default.Check else Icons.Default.Refresh,
                contentDescription = "Revision",
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(Modifier.height(6.dp))

        Text(
            text = if (isRevised) "Revised" else "Revise",
            style = MaterialTheme.typography.bodySmall,
            color = if (isRevised) Color(0xFFB9FFC2) else Color.White
        )
    }
}
