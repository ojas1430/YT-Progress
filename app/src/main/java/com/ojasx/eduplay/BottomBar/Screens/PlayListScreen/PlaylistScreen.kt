package com.ojasx.eduplay.BottomBar.Screens.PlayListScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ojasx.eduplay.API.VideoCard.LinkPlaylistScreen
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.R
import com.ojasx.eduplay.StatusBar

@Composable
fun PlaylistScreen(
    playlistviewModel: PlaylistViewModel = viewModel(),
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        MyPlaylistBanner()

        //All playlist videos are displayed
        LinkPlaylistScreen(playlistviewModel)
    }
}