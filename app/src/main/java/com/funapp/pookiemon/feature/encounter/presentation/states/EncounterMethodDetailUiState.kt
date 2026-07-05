package com.funapp.pookiemon.feature.encounter.presentation.states

import com.funapp.pookiemon.feature.encounter.domain.model.EncounterMethod

data class EncounterMethodDetailUiState(
    val method: EncounterMethod? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
