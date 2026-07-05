package com.funapp.pookiemon.feature.berry.presentation.states

import com.funapp.pookiemon.feature.berry.domain.model.Berry

data class BerryDetailUiState(
    val berry: Berry? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
