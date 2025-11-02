package com.ojasx.eduplay.BottomBar.Screens.PlayListScreen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.API.VideoCard.LinkPlaylistScreen
import com.ojasx.eduplay.BottomBar.Screens.SoftBlurBackground

@Composable
fun PlaylistScreen(
    playlistviewModel: PlaylistViewModel = viewModel(),
    navController: NavController
) {


    Box(modifier = Modifier.fillMaxSize()) {
        SoftBlurBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            MyPlaylistBanner()

            // 🎥 All playlist videos
            LinkPlaylistScreen(playlistviewModel)

            Spacer(modifier = Modifier.height(12.dp))
        }

        PagerButtons(
            currentPage = playlistviewModel.currentPage.value,
            totalPages = playlistviewModel.totalPages.value,
            onPageChange = { page ->
                playlistviewModel.onPageChange(page)
            }
        )

    }
}
