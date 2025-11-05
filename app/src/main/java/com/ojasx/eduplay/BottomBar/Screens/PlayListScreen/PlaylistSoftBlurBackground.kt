package com.ojasx.eduplay.BottomBar.Screens.PlayListScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ojasx.eduplay.ui.theme.cardcolor

@Composable
fun PlaylistSoftBlurBackground() {

    val PurpleGradient = Color(0xFFA770EF)
    val PinkGradient = Color(0xFFCF8BF3)
    val PeachGradient = Color(0xFFFDB99B)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        PurpleGradient,
                        PinkGradient,
                        PeachGradient
                    )
                )
            )
            .blur(60.dp)
    )
}
