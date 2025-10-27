package com.ojasx.eduplay.API.VideoCard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ojasx.eduplay.API.PlaylistItem

@Composable
fun PlaylistItemCard(
    item: PlaylistItem,
    onCheckedChange: (Boolean)-> Unit,
    onNotesClick:()-> Unit,
    onRevisionClick:()->Unit,
    isPinned:(Boolean)-> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            //Thunbnail
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = item.snippet.thumbnails?.default?.url,
                    contentDescription = item.snippet.title,
                    modifier = Modifier.matchParentSize()
                )
                // Gradient overlay for title readability
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                                startY = 100f
                            )
                        )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))


            //Title
            Text(
                text = item.snippet.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))


            // Row: Checkbox + Notes + Revision
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                    // checkbox
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Checkbox(
                            checked = item.isCompleted,
                            onCheckedChange = onCheckedChange
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "Status",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }


                //Notes
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = onNotesClick) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Notes"
                        )
                    }
                    Text(
                        "Note",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Revision button
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = onRevisionClick) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Revision"
                        )
                    }
                    Text(
                        "Revision",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Pin Videos
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    PinStarButton(
                        isPinned = item.isPinned,
                        onPinClick = isPinned
                    )
                    Text(
                        "Pin video",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

            }
        }
    }
}
