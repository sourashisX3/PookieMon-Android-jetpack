package com.funapp.pookiemon.core.data.settings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "pookiemon_settings")

@Singleton
class SettingsDataStore @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    companion object {
        private val KEY_PALETTE = stringPreferencesKey("color_palette")
        private val KEY_DARK_MODE = stringPreferencesKey("dark_mode")
    }

    val paletteName: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_PALETTE] ?: "default"
    }

    val darkMode: Flow<DarkModePreference> = context.dataStore.data.map { prefs ->
        val value = prefs[KEY_DARK_MODE]
        when (value) {
            "light" -> DarkModePreference.LIGHT
            "dark" -> DarkModePreference.DARK
            else -> DarkModePreference.SYSTEM
        }
    }

    suspend fun setPalette(name: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_PALETTE] = name
        }
    }

    suspend fun setDarkMode(preference: DarkModePreference) {
        context.dataStore.edit { prefs ->
            when (preference) {
                DarkModePreference.LIGHT -> prefs[KEY_DARK_MODE] = "light"
                DarkModePreference.DARK -> prefs[KEY_DARK_MODE] = "dark"
                DarkModePreference.SYSTEM -> prefs.remove(KEY_DARK_MODE)
            }
        }
    }
}

enum class DarkModePreference {
    SYSTEM, LIGHT, DARK
}
