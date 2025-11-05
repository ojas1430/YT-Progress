package com.ojasx.eduplay.API.VideoCard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ojasx.eduplay.API.PlaylistItem
import com.ojasx.eduplay.API.PlaylistViewModel

@Composable
fun LinkPlaylistScreen(viewModel: PlaylistViewModel = viewModel()) {

    val playlistItems by remember { derivedStateOf { viewModel.playlistItems.value } }

    // Sort pinned videos on top
    val sortedList = playlistItems.sortedByDescending { it.isPinned }
    val currentPage by viewModel.currentPage
    val listState = rememberLazyListState()

    LaunchedEffect(currentPage) {
        if (sortedList.isNotEmpty()) {
            listState.animateScrollToItem(0)
        }
    }

    LazyColumn(state = listState) {
        items(sortedList) { item ->

            PlaylistItemCard(
                item = item,
                onCheckedChange = { checked ->
                    item.isCompleted = checked
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