package com.ojasx.eduplay.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.ui.BottomBar.BottomBar
import com.ojasx.eduplay.ui.Reusables.StatusBar

@Composable
fun HomePage(
    navController: NavController,
    playlistViewModel: PlaylistViewModel
    ) {
    StatusBar()
    Box(
        modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
    ){
        BottomBar(
            playlistViewModel,
            navController
            )
    }
}