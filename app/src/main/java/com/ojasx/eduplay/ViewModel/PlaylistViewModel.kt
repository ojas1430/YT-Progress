
package com.ojasx.eduplay.API

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.ojasx.eduplay.BuildConfig
import com.ojasx.eduplay.Data.Local.RoomDataBase.AppDatabase
import com.ojasx.eduplay.Data.Local.RoomDataBase.VideoStateEntity
import kotlinx.coroutines.launch

class PlaylistViewModel(application: Application) : AndroidViewModel(application) {

    //Room database instance
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "playlist_db"
    ).fallbackToDestructiveMigration()
        .build()

    private val videoStateDao = db.videoStateDao()

    private val completedMap = mutableStateMapOf<String, Boolean>()
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
        loadVideoStates()
    }



    fun fetchPlaylistVideos(
        initial: Boolean = true,
        pageToken: String? = null
    ) {
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

            playlistItems.value = response.items?.map { item ->
                val videoId = item.snippet.resourceId?.videoId
                item.copy(
                    isCompleted = completedMap[videoId] ?: false
                )
            } ?: emptyList()


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

    fun updateCompleted(videoId: String, completed: Boolean) {
        completedMap[videoId] = completed

        playlistItems.value = playlistItems.value.map {
            if (it.snippet.resourceId?.videoId == videoId) {
                it.copy(isCompleted = completed)
            } else it
        }
        upsertVideoState(videoId, isCompleted = completed)
    }

    fun updatePinned(videoId: String, pinned: Boolean) {
        playlistItems.value = playlistItems.value.map {
            if (it.snippet.resourceId?.videoId == videoId) {
                it.copy(isPinned = pinned)
            } else it
        }
    }

    fun updateRevise(videoId: String, revised: Boolean) {

        playlistItems.value = playlistItems.value.map { item ->
            if (item.snippet.resourceId?.videoId == videoId) {
                item.copy(needsRevision = revised)
            } else item
        }
    }

    fun updateNote(videoId: String, note: String) {
        playlistItems.value = playlistItems.value.map { item ->
            if (item.snippet.resourceId?.videoId == videoId) {
                item.copy(note = note)
            } else item
        }
    }

    private fun upsertVideoState(
        videoId: String,
        isCompleted: Boolean? = null,
        needsRevision: Boolean? = null,
        isPinned: Boolean? = null,
        note: String? = null
    ) {
        viewModelScope.launch {
            val state = VideoStateEntity(
                videoId = videoId,
                isCompleted = isCompleted ?: false,
                needsRevision = needsRevision ?: false,
                isPinned = isPinned ?: false,
                note = note
            )
            videoStateDao.insert(state)
        }
    }

    private fun loadVideoStates() {
        viewModelScope.launch {
            val states = videoStateDao.getAllStates()
            val stateMap = states.associateBy { it.videoId }

            playlistItems.value = playlistItems.value.map { item ->
                val videoId = item.snippet.resourceId?.videoId
                val state = stateMap[videoId]

                if (state != null) {
                    item.copy(
                        isCompleted = state.isCompleted,
                        isPinned = state.isPinned,
                        needsRevision = state.needsRevision,
                        note = state.note
                    )
                } else item
            }
        }
    }


    fun applySort(sort: String) {
        playlistItems.value = when (sort) {

            "Completed Watching" -> playlistItems.value
                .sortedByDescending { it.isCompleted }

            "Revise" -> playlistItems.value
                .sortedByDescending { it.needsRevision }

            "Pinned" -> playlistItems.value
                .sortedByDescending { it.isPinned }

            else -> playlistItems.value // Default
        }
    }



}
