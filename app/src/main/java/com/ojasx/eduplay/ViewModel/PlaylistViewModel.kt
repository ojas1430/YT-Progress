
package com.ojasx.eduplay.API

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.ojasx.eduplay.BuildConfig
import com.ojasx.eduplay.Data.Local.Preferences.PlaylistPreferences
import com.ojasx.eduplay.Data.Local.RoomDataBase.AppDatabase
import com.ojasx.eduplay.Data.Local.RoomDataBase.VideoStateEntity
import kotlinx.coroutines.flow.firstOrNull
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
    private val preferences = PlaylistPreferences(application.applicationContext)

    private val repository = YouTubeRepository(db.playlistDao())
    var playlistLink = mutableStateOf("")
    private var allPlaylistItems = emptyList<PlaylistItem>()
    private var stateMap = emptyMap<String, VideoStateEntity>()
    var playlistItems = mutableStateOf<List<PlaylistItem>>(emptyList())
    var errorMessage = mutableStateOf<String?>(null)
    var isLoading = mutableStateOf(false)
    private var currentSort: String = "Default"

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
        restoreLastPlaylistAndRefresh()
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

            preferences.saveLastPlaylistId(id)
            allPlaylistItems = response.items
                ?.map { item -> applyStateToItem(item, stateMap[item.snippet.resourceId?.videoId]) }
                ?: emptyList()
            applySort(currentSort)


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
                allPlaylistItems = cached
                    .map { it.toPlaylistItem() }
                    .map { item -> applyStateToItem(item, stateMap[item.snippet.resourceId?.videoId]) }
                applySort(currentSort)
            }
        }
    }

    private fun restoreLastPlaylistAndRefresh() {
        viewModelScope.launch {
            val cachedPlaylistId = preferences.lastPlaylistIdFlow.firstOrNull()
            if (cachedPlaylistId.isNullOrBlank()) return@launch

            playlistId = cachedPlaylistId
            playlistLink.value = cachedPlaylistId
            fetchPlaylistVideos(initial = false)
        }
    }

    fun updateCompleted(videoId: String, completed: Boolean) {
        allPlaylistItems = allPlaylistItems.map {
            if (it.snippet.resourceId?.videoId == videoId) it.copy(isCompleted = completed) else it
        }
        applySort(currentSort)
        upsertVideoState(videoId, isCompleted = completed)
    }

    fun updatePinned(videoId: String, pinned: Boolean) {
        allPlaylistItems = allPlaylistItems.map {
            if (it.snippet.resourceId?.videoId == videoId) it.copy(isPinned = pinned) else it
        }
        applySort(currentSort)
        upsertVideoState(videoId, isPinned = pinned)
    }

    fun updateRevise(videoId: String, revised: Boolean) {

        allPlaylistItems = allPlaylistItems.map { item ->
            if (item.snippet.resourceId?.videoId == videoId) {
                item.copy(needsRevision = revised)
            } else item
        }
        applySort(currentSort)
        upsertVideoState(videoId, needsRevision = revised)
    }

    fun updateNote(videoId: String, note: String) {
        allPlaylistItems = allPlaylistItems.map { item ->
            if (item.snippet.resourceId?.videoId == videoId) {
                item.copy(note = note)
            } else item
        }
        applySort(currentSort)
        upsertVideoState(videoId, note = note)
    }

    private fun upsertVideoState(
        videoId: String,
        isCompleted: Boolean? = null,
        needsRevision: Boolean? = null,
        isPinned: Boolean? = null,
        note: String? = null
    ) {
        viewModelScope.launch {
            val existing = videoStateDao.getState(videoId)
            val state = VideoStateEntity(
                videoId = videoId,
                isCompleted = isCompleted ?: existing?.isCompleted ?: false,
                needsRevision = needsRevision ?: existing?.needsRevision ?: false,
                isPinned = isPinned ?: existing?.isPinned ?: false,
                note = note ?: existing?.note
            )
            videoStateDao.insert(state)
            stateMap = stateMap + (videoId to state)
        }
    }

    private fun loadVideoStates() {
        viewModelScope.launch {
            val states = videoStateDao.getAllStates()
            stateMap = states.associateBy { it.videoId }
            allPlaylistItems = allPlaylistItems.map { item ->
                applyStateToItem(item, stateMap[item.snippet.resourceId?.videoId])
            }
            applySort(currentSort)
        }
    }

    private fun applyStateToItem(item: PlaylistItem, state: VideoStateEntity?): PlaylistItem {
        if (state == null) return item
        return item.copy(
            isCompleted = state.isCompleted,
            isPinned = state.isPinned,
            needsRevision = state.needsRevision,
            note = state.note
        )
    }


    fun applySort(sort: String) {
        currentSort = sort
        playlistItems.value = when (sort) {

            "Completed Watching" -> allPlaylistItems
                .filter { it.isCompleted  }

            "Revise" -> allPlaylistItems
                .filter { it.needsRevision}

            "Pinned" -> allPlaylistItems
                .filter { it.isPinned  }

            else -> allPlaylistItems
                
        }
    }
}
