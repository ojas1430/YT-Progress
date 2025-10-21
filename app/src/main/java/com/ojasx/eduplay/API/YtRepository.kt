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
            Log.d("YouTubeRepo", "=== Starting API Request ===")
            Log.d("YouTubeRepo", "Playlist ID: $playlistId")
            Log.d("YouTubeRepo", "API Key: ${apiKey.take(10)}...${apiKey.takeLast(5)}")
            Log.d("YouTubeRepo", "API Key length: ${apiKey.length}")
            Log.d("YouTubeRepo", "Page Token: $pageToken")

            val requestUrl = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId=$playlistId&key=${apiKey.take(10)}..."
            Log.d("YouTubeRepo", "Request URL: $requestUrl")

            Log.d("YouTubeRepo", "Making API call...")
            val response = api.getPlaylistItems(
                playlistId = playlistId,
                apiKey = apiKey,
                pageToken = pageToken
            ).await()

            Log.d("YouTubeRepo", "=== API Response Received ===")
            Log.d("YouTubeRepo", "Response is not null: ${response != null}")
            Log.d("YouTubeRepo", "Items count: ${response.items?.size ?: 0}")
            Log.d("YouTubeRepo", "Next page token: ${response.nextPageToken}")
            Log.d("YouTubeRepo", "Previous page token: ${response.prevPageToken}")

            if (response.items != null && response.items.isNotEmpty()) {
                Log.d("YouTubeRepo", "First item title: ${response.items[0].snippet.title}")
            }

            response
        } catch (e: Exception) {
            Log.e("YouTubeRepo", "=== API Error Occurred ===")
            Log.e("YouTubeRepo", "Exception type: ${e.javaClass.simpleName}")
            Log.e("YouTubeRepo", "Error message: ${e.message}")
            Log.e("YouTubeRepo", "Stack trace:", e)

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