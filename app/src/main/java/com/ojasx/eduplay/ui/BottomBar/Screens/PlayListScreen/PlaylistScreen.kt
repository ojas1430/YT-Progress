package com.ojasx.eduplay.ui.BottomBar.Screens.PlayListScreen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.API.VideoCard.LinkPlaylistScreen
import com.ojasx.eduplay.ui.BottomBar.Screens.PlayListScreen.Stats.StatsButton

@Composable
fun PlaylistScreen(
    playlistviewModel: PlaylistViewModel = viewModel(),
    navController: NavController,
) {



    LaunchedEffect(Unit) {
        playlistviewModel.loadStats()
    }

    var selectedSort by remember { mutableStateOf("Default") }

    Box(modifier = Modifier
        .fillMaxSize()
        .statusBarsPadding()
    ) {
        PlaylistSoftBlurBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            MyPlaylistBanner(
                playlistviewModel,
                selectedSort = selectedSort,
                onSortSelected = { sort ->
                    selectedSort = sort
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                StatsButton(playlistviewModel,navController)

                PagerButtons(
                    currentPage = playlistviewModel.currentPage.value,
                    totalPages = playlistviewModel.totalPages.value,
                    onPageChange = { page ->
                        playlistviewModel.onPageChange(page)
                    }
                )
            }

            // 🎥 All playlist videos
            LinkPlaylistScreen(
                playlistviewModel,
                navController,
                selectedSort = selectedSort
                )
        }

    }
}