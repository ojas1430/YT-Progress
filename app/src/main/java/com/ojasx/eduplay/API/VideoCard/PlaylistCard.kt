package com.ojasx.eduplay.API.VideoCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ojasx.eduplay.API.PlaylistItem
import com.ojasx.eduplay.API.VideoCard.CardFeatures.CompletedCheckbox
import com.ojasx.eduplay.API.VideoCard.CardFeatures.Notes.Notes
import com.ojasx.eduplay.API.VideoCard.CardFeatures.Pin.PinButton
import com.ojasx.eduplay.API.VideoCard.CardFeatures.ReviseButton

@Composable
fun PlaylistItemCard(
    item: PlaylistItem,
    onCheckedChange: (Boolean) -> Unit,
    onNotesClick: (String) -> Unit,
    onRevisionClick: (Boolean) -> Unit,
    isPinned: (Boolean) -> Unit
) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .shadow(10.dp, RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A72)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0x33FFFFFF),
                            Color(0x22FFFFFF)
                        )
                    )
                )
                .padding(16.dp)
        ) {

            // --- Thumbnail ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = item.snippet.thumbnails?.medium?.url,
                    contentDescription = item.snippet.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )

                // Subtle gradient overlay for readability
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.55f))
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- Title ---
            Text(
                text = item.snippet.title,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // --- Row: Checkbox + Notes + Revision + Pin ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // ✅ Checkbox
                CompletedCheckbox(
                    isInitiallyChecked = item.isCompleted,
                    onCheckedChange = onCheckedChange
                )


                // 📝 Notes
                Notes(
                    noteText = item.note ?: "",
                    onNotesSave = { newNote ->
                        item.note = newNote
                        onNotesClick(newNote)
                    }
                )

                // 🔁 Revision
                ReviseButton(
                    isRevised = item.needsRevision,
                    onToggle = {
                        !item.needsRevision
                    }
                )

                // 📌 Pin
                PinButton(
                    isPin = item.isPinned,
                    onPinChanged = {
                        isPinned
                    }
                )

            }
        }
    }
}