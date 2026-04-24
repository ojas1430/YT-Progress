
package com.ojasx.eduplay.API.VideoCard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ojasx.eduplay.API.PlaylistViewModel

@Composable
fun LinkPlaylistScreen(
    viewModel: PlaylistViewModel = viewModel(),
    navController: NavController
) {

    val playlistItems = viewModel.playlistItems.value
    val listState = rememberLazyListState()
    val currentPage by viewModel.currentPage

    LaunchedEffect(currentPage) {
        if (playlistItems.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 6.dp)
    ) {
        items(playlistItems) { item ->

            PlaylistItemCard(
                viewModel,
                item = item,
                onCheckedChange = { checked ->
                    item.isCompleted = checked
                },
                onVideoClick = { videoId ->
                    navController.navigate("player/$videoId")
                },
                onNotesClick = {
                    // Open notes dialog / bottom sheet here
                    println("Notes clicked for ${item.snippet.title}")
                },
                onRevisionClick = {
                    item.needsRevision = !item.needsRevision
                },
                isPinned = { pinned -> item.isPinned = pinned  }
            )


        }
    }
}
