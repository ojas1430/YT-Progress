package com.ojasx.eduplay.API

import android.util.Log
import retrofit2.await

class YouTubeRepository {
    private val api = RetrofitClient.apiService

    // Fetch only one page of videos at a time
    suspend fun getPlaylistVideosPage(
        playlistId: String,
        apiKey: String,
        pageToken: String? = null
    ): YouTubePlaylistResponse? {
        return try {
            val response = api.getPlaylistItems(
                playlistId = playlistId,
                apiKey = apiKey,
                pageToken = pageToken
            ).await()

            response
        } catch (e: Exception) {
            Log.e("YouTubeRepo", "Error fetching playlist page: ${e.message}", e)
            null
        }
    }

}
