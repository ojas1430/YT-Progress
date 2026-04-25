package com.ojasx.eduplay.Data.Local.Preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class PlaylistPreferences(context: Context) {

    private val dataStore = PreferenceDataStoreFactory.create(
        produceFile = { context.preferencesDataStoreFile(DATASTORE_NAME) }
    )

    val lastPlaylistIdFlow: Flow<String?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences -> preferences[LAST_PLAYLIST_ID] }

    suspend fun saveLastPlaylistId(playlistId: String) {
        dataStore.edit { preferences ->
            preferences[LAST_PLAYLIST_ID] = playlistId
        }
    }

    companion object {
        private const val DATASTORE_NAME = "playlist_prefs"
        private val LAST_PLAYLIST_ID = stringPreferencesKey("last_playlist_id")
    }
}
