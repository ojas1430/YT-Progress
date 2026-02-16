package com.ojasx.eduplay.API

import com.ojasx.eduplay.Data.Local.RoomDataBase.PlaylistEntity


fun PlaylistItem.toEntity(old : PlaylistEntity?): PlaylistEntity? {
    val snippet = snippet ?: return null
    return PlaylistEntity(
        videoId = snippet.resourceId?.videoId ?: return null,
        title = snippet.title,
        description = snippet.description,
        thumbnailUrl = snippet.thumbnails?.medium?.url,
        isCompleted = old?.isCompleted ?: false,
        note = old?.note,
        needsRevision = old?.needsRevision?:false,
        isPinned = old?.isPinned?:false
    )
}

fun PlaylistEntity.toPlaylistItem(): PlaylistItem {
    val snippet = Snippet(
        title = title,
        description = description,
        thumbnails = null,
        resourceId = ResourceId(videoId)
    )
    return PlaylistItem(snippet, isCompleted, note, needsRevision, isPinned)
}
