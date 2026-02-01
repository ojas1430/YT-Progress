package com.ojasx.eduplay.Data.Local.RoomDataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlaylistDao {

    @Query("Select * from playlist_videos")
    suspend fun getAllVideos() : List<PlaylistEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(videos : List<PlaylistEntity>)

    @Query("DELETE FROM playlist_videos")
    suspend fun clearAll()

    @Update
    suspend fun updateVideo(video: PlaylistEntity)

    @Query("UPDATE playlist_videos SET isPinned = :value WHERE videoId = :videoId")
    suspend fun setPinned(videoId: String,value : Boolean)

    @Query("UPDATE playlist_videos SET isCompleted = :value WHERE videoId = :videoId")
    suspend fun setCompleted(videoId : String , value:Boolean)

    @Query("UPDATE playlist_videos SET needsRevision = :value WHERE videoId = :videoId")
    suspend fun setRevision(videoId: String, value: Boolean)



}