package com.ojasx.eduplay.ui.BottomBar.Screens.PlayListScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ojasx.eduplay.API.PlaylistViewModel
import com.ojasx.eduplay.ui.BottomBar.Screens.PlayListScreen.SortDropDownMenu.SortDropdownMenu

@Composable
fun MyPlaylistBanner(
    playlistviewModel : PlaylistViewModel,
    selectedSort: String,
    onSortSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "My Playlists ▶️",
            // Increased from titleMedium to headlineSmall for a larger, dynamic scale
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )

        SortDropdownMenu(
            selectedSort = selectedSort,
            onSortSelected = { sort ->
                onSortSelected(sort)
                playlistviewModel.applySort(sort)
            }
        )
    }
}