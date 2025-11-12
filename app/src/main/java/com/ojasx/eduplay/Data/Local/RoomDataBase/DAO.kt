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

}