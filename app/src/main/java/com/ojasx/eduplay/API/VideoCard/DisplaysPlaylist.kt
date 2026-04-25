
package com.ojasx.eduplay.API.VideoCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.R

@Composable
fun LinkPlaylistScreen(
    viewModel: PlaylistViewModel = viewModel(),
    navController: NavController,
    selectedSort: String
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
        if (playlistItems.isEmpty()) {
            item {
                EmptyPlaylistCard(
                    isLoading = viewModel.isLoading.value,
                    selectedSort = selectedSort
                )
            }
        }

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

@Composable
private fun EmptyPlaylistCard(
    isLoading: Boolean,
    selectedSort: String
) {
    val title = when {
        isLoading -> "Fetching videos..."
        selectedSort == "Completed Watching" -> "No completed videos yet"
        selectedSort == "Revise" -> "Nothing marked for revision"
        selectedSort == "Pinned" -> "No pinned videos yet"
        else -> "No videos to show"
    }

    val subtitle = when {
        isLoading -> "Please wait while your playlist is loading."
        selectedSort == "Completed Watching" -> "Mark videos as completed to see them here."
        selectedSort == "Revise" -> "Tap Revise on any card to build your revision list."
        selectedSort == "Pinned" -> "Pin videos to keep important ones in this tab."
        else -> "Fetch a playlist from Home to start tracking."
    }

    // Centering the card in the middle of the parent screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                // Adding a subtle border for visual depth
                .border(
                    width = 1.dp,
                    color = Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(28.dp)
                ),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.2f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Logo Section (Replace R.drawable.your_logo with your actual resource)
                Image(
                    painter = painterResource(id = R.drawable.empty),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 24.dp),
                    colorFilter = ColorFilter.tint(Color.White)
                )

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                if (isLoading) {
                    Spacer(modifier = Modifier.height(24.dp))
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}