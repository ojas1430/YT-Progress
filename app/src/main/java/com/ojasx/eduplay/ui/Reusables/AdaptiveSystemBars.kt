package com.ojasx.eduplay.ui.Reusables

import android.app.Activity
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun AdaptiveSystemBars(
    statusBarColor: Color,
    navigationBarColor: Color
) {
    val view = LocalView.current
    if (view.isInEditMode) return

    val activity = view.context.findActivity() ?: return
    SideEffect {
        val window = activity.window
        window.statusBarColor = statusBarColor.toArgb()
        window.navigationBarColor = navigationBarColor.toArgb()

        val controller = WindowCompat.getInsetsController(window, view)
        controller.isAppearanceLightStatusBars = statusBarColor.luminance() > 0.5f
        controller.isAppearanceLightNavigationBars = navigationBarColor.luminance() > 0.5f
    }
}

private tailrec fun android.content.Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
