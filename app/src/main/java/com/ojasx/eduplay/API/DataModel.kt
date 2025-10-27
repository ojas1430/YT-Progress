package com.ojasx.eduplay.API

import com.google.gson.annotations.SerializedName

data class YouTubePlaylistResponse(
    val items: List<PlaylistItem>?,
    val nextPageToken: String?,
    val prevPageToken: String?
)

data class PlaylistItem(
    val snippet: Snippet,
    var isCompleted : Boolean = false,
    var hasNotes : Boolean = false,
    var needsRevision : Boolean = false,
    var isPinned: Boolean = false
)

data class Snippet(
    val title: String,
    val description: String?,
    val thumbnails: Thumbnails?,
    @SerializedName("resourceId") val resourceId: ResourceId?
)

data class Thumbnails(
    val default: Thumbnail?,
    val medium: Thumbnail?,
    val high: Thumbnail?
)

data class Thumbnail(
    val url: String,
    val width: Int,
    val height: Int
)

data class ResourceId(
    val videoId: String
)