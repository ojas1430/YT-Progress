package com.ojasx.eduplay.API

import android.util.Log
import com.ojasx.eduplay.Data.Local.RoomDataBase.PlaylistDao
import com.ojasx.eduplay.Data.Local.RoomDataBase.PlaylistEntity
import com.ojasx.eduplay.Data.Local.RoomDataBase.VideoStateDao
import com.ojasx.eduplay.Data.Local.RoomDataBase.VideoStateEntity
import retrofit2.await

class YouTubeRepository(
    private val dao: PlaylistDao? = null
) {
    private val api = RetrofitClient.apiService

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

            // Save API data to Room if available
            response?.items?.let { items ->

                // 1️Load existing Room data
                val localVideos = dao?.getAllVideos().orEmpty()
                val localMap = localVideos.associateBy { it.videoId }

                //  Merge API data with local user state
                val mergedEntities = items.mapNotNull { item ->
                    val videoId = item.snippet?.resourceId?.videoId
                    val oldEntity = localMap[videoId]
                    item.toEntity(oldEntity)   // 👈 PRESERVES notes, done, pinned
                }

                // Save merged data
                dao?.insertAll(mergedEntities)
            }


            response
        } catch (e: Exception) {
            Log.e("YouTubeRepo", "Error fetching playlist page: ${e.message}", e)
            // Load cached videos if API fails
            val cached = dao?.getAllVideos()
            if (cached != null && cached.isNotEmpty()) {
                YouTubePlaylistResponse(
                    items = cached.map { it.toPlaylistItem() },
                    nextPageToken = null,
                    prevPageToken = null
                )
            } else null
        }
    }

    suspend fun getCachedVideos() : List<PlaylistEntity>{
        return dao?.getAllVideos()?:emptyList()
    }
    suspend fun clearCache() {
        dao?.clearAll()
    }




}