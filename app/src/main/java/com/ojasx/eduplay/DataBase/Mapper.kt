package com.ojasx.eduplay.API

import com.ojasx.eduplay.DataBase.PlaylistEntity


fun PlaylistItem.toEntity(): PlaylistEntity? {
    val snippet = snippet ?: return null
    return PlaylistEntity(
        videoId = snippet.resourceId?.videoId ?: return null,
        title = snippet.title,
        description = snippet.description,
        thumbnailUrl = snippet.thumbnails?.medium?.url,
        isCompleted = isCompleted,
        hasNotes = hasNotes,
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
    return PlaylistItem(snippet, isCompleted, hasNotes, needsRevision, isPinned)
}
