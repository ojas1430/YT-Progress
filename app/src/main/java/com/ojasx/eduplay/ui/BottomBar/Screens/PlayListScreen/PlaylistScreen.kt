package com.ojasx.eduplay.ui.BottomBar.Screens.PlayListScreen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.API.VideoCard.LinkPlaylistScreen

@Composable
fun PlaylistScreen(
    playlistviewModel: PlaylistViewModel = viewModel(),
    navController: NavController,
) {

    var selectedSort by remember { mutableStateOf("Default") }

    Box(modifier = Modifier.fillMaxSize()) {
        PlaylistSoftBlurBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
                MyPlaylistBanner(
                    selectedSort = selectedSort,
                    onSortSelected = {selectedSort = it}
                )

            PagerButtons(
                currentPage = playlistviewModel.currentPage.value,
                totalPages = playlistviewModel.totalPages.value,
                onPageChange = { page ->
                    playlistviewModel.onPageChange(page)
                }
            )

            // 🎥 All playlist videos
            LinkPlaylistScreen(playlistviewModel)
        }



    }
}