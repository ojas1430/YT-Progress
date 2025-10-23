package com.ojasx.eduplay

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun StatusBar() {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = Color(0xFF3C4A5C)

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = false
        )
    }
}