package com.funapp.pookiemon.feature.encounter.presentation.states

import com.funapp.pookiemon.feature.encounter.domain.model.EncounterMethod

data class EncounterMethodListUiState(
    val methods: List<EncounterMethod> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val hasMore: Boolean = true,
    val currentOffset: Int = 0,
)
