package com.ojasx.eduplay.Data.Local.RoomDataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_videos")
data class PlaylistEntity(
    @PrimaryKey val videoId: String,
    val title: String,
    val description: String?,
    val thumbnailUrl: String?,
    val isCompleted: Boolean = false,
    val note: String? = null,
    val needsRevision: Boolean = false,
    val isPinned: Boolean = false
)

// this is for sorting
@Entity(tableName = "video_state")
data class VideoStateEntity(
    @PrimaryKey val videoId: String,
    val isCompleted: Boolean = false,
    val needsRevision: Boolean = false,
    val isPinned: Boolean = false,
    val note: String? = null
)
