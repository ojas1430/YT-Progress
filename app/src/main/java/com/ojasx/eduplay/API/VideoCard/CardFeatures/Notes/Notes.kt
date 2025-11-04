package com.ojasx.eduplay.API.VideoCard.CardFeatures.Notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Notes(
    noteText: String,
    onNotesSave: (String) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }
    var noteText by remember { mutableStateOf(noteText) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    brush = Brush.linearGradient(
                        listOf(Color(0xFFcf8bf3), Color(0xFFfdb99b))
                    ),
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Notes",
                tint = Color.White,
                modifier = Modifier.size(18.dp)
                    .clickable(
                        onClick = {
                            showDialog = true
                        }
                    )
            )
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = "Notes",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
    }
    if (showDialog) {
        NotesDialog(
            initialNote = noteText,
            onDismiss = { showDialog = false },
            onSave = { newNote ->
                noteText = newNote
                onNotesSave(newNote)

            }
        )

    }
}
