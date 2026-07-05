package com.funapp.pookiemon.feature.berry.presentation.states

import com.funapp.pookiemon.feature.berry.domain.model.Berry

data class BerryListUiState(
    val berries: List<Berry> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val hasMore: Boolean = true,
    val currentOffset: Int = 0,
)
