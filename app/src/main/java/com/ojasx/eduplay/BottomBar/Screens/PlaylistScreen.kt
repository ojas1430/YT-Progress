package com.ojasx.eduplay.BottomBar.Screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ojasx.eduplay.API.LinkPlaylistScreen
import com.ojasx.eduplay.API.PlaylistViewModel

@Composable
fun PlaylistScreen(
    playlistviewModel: PlaylistViewModel = viewModel(),
    navController: NavController
) {
    LinkPlaylistScreen(playlistviewModel)
}