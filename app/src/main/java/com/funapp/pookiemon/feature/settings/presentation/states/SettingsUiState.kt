package com.funapp.pookiemon.feature.settings.presentation.states

import com.funapp.pookiemon.core.data.settings.DarkModePreference
import com.funapp.pookiemon.core.data.settings.PaletteDefinition

data class SettingsUiState(
    val currentPalette: String = "default",
    val darkModePreference: DarkModePreference = DarkModePreference.SYSTEM,
    val palettes: List<PaletteDefinition> = emptyList(),
)
