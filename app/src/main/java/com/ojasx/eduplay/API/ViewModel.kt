package com.ojasx.eduplay.API

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.ojasx.eduplay.BuildConfig
import com.ojasx.eduplay.Data.Local.RoomDataBase.AppDatabase
import kotlinx.coroutines.launch

class PlaylistViewModel(application: Application) : AndroidViewModel(application) {

    //Room database instance
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "playlist_db"
    ).fallbackToDestructiveMigration()
        .build()

    private val repository = YouTubeRepository(db.playlistDao())
    var playlistLink = mutableStateOf("")
    var playlistItems = mutableStateOf<List<PlaylistItem>>(emptyList())
    var errorMessage = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)

    // Pagination state
    var currentPage = mutableStateOf(1)
    var totalPages = mutableStateOf(1)
    private var nextPageToken: String? = null
    private var prevPageToken: String? = null
    private var pageTokens = mutableListOf<String?>(null)
    private var playlistId: String? = null

    init {
        loadCachedVideos()
    }



    fun fetchPlaylistVideos(initial: Boolean = true, pageToken: String? = null) {
        val id = if (initial) extractPlaylistId(playlistLink.value.trim()) else playlistId
        if (id.isNullOrEmpty()) {
            errorMessage.value = "Invalid playlist link"
            return
        }

        playlistId = id
        val apiKey = BuildConfig.YOUTUBE_API_KEY
        isLoading.value = true
        errorMessage.value = null

        viewModelScope.launch {
            val response = repository.getPlaylistVideosPage(id, apiKey, pageToken)
            isLoading.value = false

            if (response == null) {
                errorMessage.value = "Failed to load videos"
                return@launch
            }

            playlistItems.value = response.items ?: emptyList()
            nextPageToken = response.nextPageToken
            prevPageToken = response.prevPageToken

            // Save page tokens
            if (pageTokens.size < currentPage.value + 1) {
                pageTokens.add(nextPageToken)
            }

            // Estimate total pages if available (optional)
            totalPages.value = pageTokens.filterNotNull().size + 1
        }
    }

    fun onPageChange(page: Int) {
        if (page < 1) return

        val token = when {
            page > currentPage.value -> nextPageToken
            page < currentPage.value -> prevPageToken
            else -> null
        }

        currentPage.value = page
        fetchPlaylistVideos(initial = false, pageToken = token)
    }

    private fun extractPlaylistId(url: String): String? {
        val regex = "list=([a-zA-Z0-9_-]+)".toRegex()
        val match = regex.find(url)
        return match?.groupValues?.get(1) ?: url.takeIf {
            it.matches(Regex("^[a-zA-Z0-9_-]+$"))
        }
    }

    private fun loadCachedVideos() {
        viewModelScope.launch {
            val cached = repository.getCachedVideos()
            if (cached.isNotEmpty()) {
                playlistItems.value = cached.map { it.toPlaylistItem() }
            }
        }
    }

}
