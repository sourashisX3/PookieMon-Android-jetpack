package com.funapp.pookiemon.feature.location.presentation.states

import com.funapp.pookiemon.feature.location.domain.model.Location

data class LocationDetailUiState(
    val location: Location? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
