package com.ojasx.eduplay.Data.Local.RoomDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        PlaylistEntity::class,
        VideoStateEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun playlistDao(): PlaylistDao
    abstract fun videoStateDao(): VideoStateDao

    companion object {

        // Existing migration (keep it)
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE playlist_videos ADD COLUMN note TEXT"
                )
            }
        }

        // NEW migration: add video_state table
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS video_state (
                        videoId TEXT NOT NULL PRIMARY KEY,
                        isCompleted INTEGER NOT NULL DEFAULT 0,
                        needsRevision INTEGER NOT NULL DEFAULT 0,
                        isPinned INTEGER NOT NULL DEFAULT 0,
                        note TEXT
                    )
                    """.trimIndent()
                )
            }
        }
    }
}
