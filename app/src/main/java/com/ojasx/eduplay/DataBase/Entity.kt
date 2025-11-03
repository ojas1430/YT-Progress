package com.ojasx.eduplay.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ojasx.eduplay.API.Snippet

@Entity(tableName = "playlist_videos")
data class PlaylistEntity(
    @PrimaryKey val videoId: String,
    val title: String,
    val description: String?,
    val thumbnailUrl: String?,
    val isCompleted: Boolean = false,
    val hasNotes: Boolean = false,
    val needsRevision: Boolean = false,
    val isPinned: Boolean = false
)