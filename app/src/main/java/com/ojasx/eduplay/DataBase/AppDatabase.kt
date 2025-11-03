package com.ojasx.eduplay.DataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlaylistEntity :: class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
}