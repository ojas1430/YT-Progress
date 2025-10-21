package com.ojasx.eduplay.API

fun extractPlaylistId(url: String): String? {
    return when {
        "list=" in url -> url.substringAfter("list=").substringBefore("&")
        else -> null
    }
}
