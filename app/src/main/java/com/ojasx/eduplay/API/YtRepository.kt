package com.ojasx.eduplay.API

import android.util.Log
import retrofit2.await

class YouTubeRepository {
    private val api = RetrofitClient.apiService


    // Fetches ALL videos from a playlist by following nextPageToken until null.

    suspend fun getAllPlaylistVideos(
        playlistId: String,
        apiKey: String
    ): List<PlaylistItem> {
        val allVideos = mutableListOf<PlaylistItem>()
        var nextPageToken: String? = null
        var pageCount = 0

        do {
            val response = try {
                api.getPlaylistItems(
                    playlistId = playlistId,
                    apiKey = apiKey,
                    pageToken = nextPageToken
                ).await()
            } catch (e: Exception) {

                val err = e.message ?: "unknown"
                Log.e("YouTubeRepo", "Request failed on page ${pageCount + 1}: $err")

                when {
                    err.contains("401") -> {
                        Log.e("YouTubeRepo", "401 Unauthorized: API Key is invalid or expired")
                    }
                    err.contains("403") -> {
                        Log.e("YouTubeRepo", "403 Forbidden: permission or quota issue")
                    }
                    err.contains("404") -> {
                        Log.e("YouTubeRepo", "404 Not Found: playlist not found or private")
                    }
                    err.contains("400") -> {
                        Log.e("YouTubeRepo", "400 Bad Request: invalid parameters")
                    }
                    err.contains("quota", ignoreCase = true) -> {
                        Log.e("YouTubeRepo", "Quota exceeded")
                    }
                    else -> {
                        Log.e("YouTubeRepo", "Unknown error: $err")
                    }
                }

                break
            }

            val items = response.items ?: emptyList()
            allVideos.addAll(items)
            pageCount++

            Log.d("YouTubeRepo", "Fetched page $pageCount, items: ${items.size}, total so far: ${allVideos.size}")

            nextPageToken = response.nextPageToken
        } while (nextPageToken != null)

        Log.d("YouTubeRepo", "Finished fetching playlist. Total videos fetched: ${allVideos.size}")
        return allVideos
    }
}