package com.ojasx.eduplay.BottomBar.Screens.PlayListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ojasx.eduplay.R

@Composable
fun MyPlaylistBanner() {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val bannerHeight = screenHeight * 0.06f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(bannerHeight)
            .padding(bottom = 8.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF777C6D),Color(0xFFB7B89F))
                )
            )
    ) {
        Image(
            painter = painterResource(R.drawable.myytplaylist),
            contentDescription = "",
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}