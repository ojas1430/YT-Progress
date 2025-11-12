package com.ojasx.eduplay.API

import com.ojasx.eduplay.Data.Local.RoomDataBase.PlaylistEntity


fun PlaylistItem.toEntity(): PlaylistEntity? {
    val snippet = snippet ?: return null
    return PlaylistEntity(
        videoId = snippet.resourceId?.videoId ?: return null,
        title = snippet.title,
        description = snippet.description,
        thumbnailUrl = snippet.thumbnails?.medium?.url,
        isCompleted = isCompleted,
        note = note,
        needsRevision = needsRevision,
        isPinned = isPinned
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
