package com.ojasx.eduplay.ui.BottomBar.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.ui.Reusables.StatusBar
import com.ojasx.eduplay.ui.linktextfield.LinkInputScreen

@Composable
fun HomeScreen(
    navController: NavController,
    playlistviewmodel: PlaylistViewModel
) {
    StatusBar()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECEFFF))
            .statusBarsPadding()
    ) {
        SoftBlurBackground()
        Column {
            LinkInputScreen(playlistviewmodel)
        }
    }
}