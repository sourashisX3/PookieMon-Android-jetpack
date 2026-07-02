package com.funapp.pookiemon.feature.settings.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funapp.pookiemon.core.data.settings.ColorPalettes
import com.funapp.pookiemon.core.data.settings.DarkModePreference
import com.funapp.pookiemon.core.data.settings.SettingsDataStore
import com.funapp.pookiemon.feature.settings.presentation.states.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore,
) : ViewModel() {

    val uiState: StateFlow<SettingsUiState> = combine(
        settingsDataStore.paletteName,
        settingsDataStore.darkMode,
    ) { palette, darkMode ->
        SettingsUiState(
            currentPalette = palette,
            darkModePreference = darkMode,
            palettes = ColorPalettes.all,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), SettingsUiState(palettes = ColorPalettes.all))

    fun setPalette(name: String) {
        viewModelScope.launch {
            settingsDataStore.setPalette(name)
        }
    }

    fun setDarkMode(preference: DarkModePreference) {
        viewModelScope.launch {
            settingsDataStore.setDarkMode(preference)
        }
    }
}
