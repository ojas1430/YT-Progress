package com.ojasx.eduplay.API

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ojasx.eduplay.BuildConfig
import kotlinx.coroutines.launch

class PlaylistViewModel(private val repository: YouTubeRepository = YouTubeRepository()) : ViewModel() {
    var playlistLink = mutableStateOf("")
    var playlistItems = mutableStateOf<List<PlaylistItem>>(emptyList())
    var errorMessage = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)

    fun fetchPlaylistVideos() {
        val link = playlistLink.value.trim()
        val playlistId = extractPlaylistId(link)

        Log.d("PlaylistVM", "=== Fetch Playlist Request ===")
        Log.d("PlaylistVM", "Original link: $link")
        Log.d("PlaylistVM", "Extracted playlist ID: $playlistId")

        if (playlistId.isNullOrEmpty()) {
            errorMessage.value = "Invalid playlist link"
            Log.e("PlaylistVM", "Invalid playlist link - playlistId is null or empty")
            return
        }

        val apiKey = BuildConfig.YOUTUBE_API_KEY

        // Check if API key is valid
        if (apiKey.isNullOrEmpty() || apiKey == "null" || apiKey == "YOUR_API_KEY") {
            errorMessage.value = "API Key not configured. Please check your local.properties file."
            Log.e("PlaylistVM", "API Key is null or not set properly")
            Log.e("PlaylistVM", "API Key value: $apiKey")
            return
        }

        Log.d("PlaylistVM", "API Key: ${apiKey.take(10)}...${apiKey.takeLast(5)}")
        Log.d("PlaylistVM", "API Key length: ${apiKey.length}")

        errorMessage.value = null
        isLoading.value = true

        viewModelScope.launch {
            try {
                Log.d("PlaylistVM", "Launching coroutine to fetch playlist...")
                val response = repository.getPlaylistVideos(playlistId, apiKey)

                if (response != null) {
                    val items = response.items ?: emptyList()
                    playlistItems.value = items
                    Log.d("PlaylistVM", "Successfully loaded ${items.size} items")

                    if (items.isEmpty()) {
                        errorMessage.value = "Playlist is empty or private"
                        Log.w("PlaylistVM", "Playlist returned empty items list")
                    } else {
                        Log.d("PlaylistVM", "First video title: ${items[0].snippet.title}")
                    }
                } else {
                    errorMessage.value = "Failed to fetch playlist. Check Logcat for details."
                    Log.e("PlaylistVM", "Response was null - API call failed")
                }
            } catch (e: Exception) {
                errorMessage.value = "Error: ${e.message}"
                Log.e("PlaylistVM", "Exception in fetchPlaylistVideos", e)
            } finally {
                isLoading.value = false
            }
        }
    }

    private fun extractPlaylistId(url: String): String? {
        Log.d("PlaylistVM", "Extracting playlist ID from: $url")

        // Try multiple patterns to handle different URL formats
        val patterns = listOf(
            "list=([a-zA-Z0-9_-]+)",           // Standard: ?list=...
            "&list=([a-zA-Z0-9_-]+)",          // With other params: &list=...
        )

        for (pattern in patterns) {
            val regex = pattern.toRegex()
            val match = regex.find(url)
            if (match != null) {
                val playlistId = match.groupValues[1]
                Log.d("PlaylistVM", "Extracted playlist ID: $playlistId")
                return playlistId
            }
        }

        // If no pattern matches, check if the whole string is already an ID
        if (url.matches(Regex("^[a-zA-Z0-9_-]+$"))) {
            Log.d("PlaylistVM", "Input is already a playlist ID: $url")
            return url
        }

        Log.e("PlaylistVM", "Could not extract playlist ID from: $url")
        return null
    }
}