package com.funapp.pookiemon.feature.location.presentation.states

import com.funapp.pookiemon.feature.location.domain.model.Location

data class LocationListUiState(
    val locations: List<Location> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val hasMore: Boolean = true,
    val currentOffset: Int = 0,
)
