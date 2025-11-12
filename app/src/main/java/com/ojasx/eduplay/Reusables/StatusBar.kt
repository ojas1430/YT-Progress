package com.ojasx.eduplay.Reusables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StatusBar() {
    Spacer(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()

    )
}
