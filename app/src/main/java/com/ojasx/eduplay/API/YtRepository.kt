package com.ojasx.eduplay.API

import android.util.Log
import retrofit2.await

class YouTubeRepository {
    private val api = RetrofitClient.apiService

    suspend fun getPlaylistVideos(
        playlistId: String,
        apiKey: String,
        pageToken: String? = null
    ): YouTubePlaylistResponse? {
        return try {

            val requestUrl = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId=$playlistId&key=${apiKey.take(10)}..."

            val response = api.getPlaylistItems(
                playlistId = playlistId,
                apiKey = apiKey,
                pageToken = pageToken
            ).await()

            if (response.items != null && response.items.isNotEmpty()) {
                Log.d("YouTubeRepo", "First item title: ${response.items[0].snippet.title}")
            }

            response
        } catch (e: Exception) {

            // Check for specific HTTP error codes
            val errorMessage = e.message ?: ""
            when {
                errorMessage.contains("401") -> {
                    Log.e("YouTubeRepo", "401 Unauthorized: API Key is invalid or expired")
                    Log.e("YouTubeRepo", "Action: Check your API key in local.properties")
                }
                errorMessage.contains("403") -> {
                    Log.e("YouTubeRepo", "403 Forbidden: API Key doesn't have permission or quota exceeded")
                    Log.e("YouTubeRepo", "Action: Check API restrictions and quota in Google Cloud Console")
                }
                errorMessage.contains("404") -> {
                    Log.e("YouTubeRepo", "404 Not Found: Playlist not found or private")
                    Log.e("YouTubeRepo", "Action: Verify the playlist ID and ensure it's public")
                }
                errorMessage.contains("400") -> {
                    Log.e("YouTubeRepo", "400 Bad Request: Invalid parameters")
                    Log.e("YouTubeRepo", "Action: Check playlist ID format")
                }
                errorMessage.contains("quota") || errorMessage.contains("Quota") -> {
                    Log.e("YouTubeRepo", "Quota Exceeded: Daily API quota limit reached")
                    Log.e("YouTubeRepo", "Action: Wait 24 hours or increase quota in Google Cloud Console")
                }
                else -> {
                    Log.e("YouTubeRepo", "Unknown error occurred")
                }
            }

            null
        }
    }
}