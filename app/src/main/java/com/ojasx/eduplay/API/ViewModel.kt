package com.ojasx.eduplay.API

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ojasx.eduplay.BuildConfig
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val repository: YouTubeRepository = YouTubeRepository()
) : ViewModel() {

    var playlistLink = mutableStateOf("")
    var playlistItems = mutableStateOf<List<PlaylistItem>>(emptyList())
    var errorMessage = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)

    fun fetchPlaylistVideos() {
        val link = playlistLink.value.trim()
        val playlistId = extractPlaylistId(link)

        if (playlistId.isNullOrEmpty()) {
            errorMessage.value = "Invalid playlist link"
            Log.e("PlaylistVM", "Invalid playlist link - playlistId is null or empty")
            return
        }

        val apiKey = BuildConfig.YOUTUBE_API_KEY

        if (apiKey.isNullOrEmpty() || apiKey == "null" || apiKey == "YOUR_API_KEY") {
            errorMessage.value = "API Key not configured. Please check your local.properties file."
            Log.e("PlaylistVM", "API Key is null or not set properly")
            return
        }

        errorMessage.value = null
        isLoading.value = true

        viewModelScope.launch {
            try {
                //  Automatically fetch ALL videos using pagination
                val allVideos = repository.getAllPlaylistVideos(playlistId, apiKey)

                playlistItems.value = allVideos

                if (allVideos.isEmpty()) {
                    errorMessage.value = "Playlist is empty or private"
                    Log.w("PlaylistVM", "Playlist returned empty items list")
                } else {
                    Log.d("PlaylistVM", " Total videos loaded: ${allVideos.size}")
                    Log.d("PlaylistVM", "First video: ${allVideos[0].snippet.title}")
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

        val patterns = listOf(
            "list=([a-zA-Z0-9_-]+)",
            "&list=([a-zA-Z0-9_-]+)",
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

    // Checkbox toggle logic
    fun toggleCompleted(item: PlaylistItem, checked: Boolean) {
        val updatedList = playlistItems.value.map {
            if (it.snippet.resourceId?.videoId == item.snippet.resourceId?.videoId) {
                it.copy(isCompleted = checked)
            } else it
        }
        playlistItems.value = updatedList
    }
}