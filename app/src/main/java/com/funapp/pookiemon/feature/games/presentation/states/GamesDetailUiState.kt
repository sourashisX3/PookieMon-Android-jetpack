package com.funapp.pookiemon.feature.games.presentation.states

import com.funapp.pookiemon.feature.games.domain.model.Generation

data class GamesDetailUiState(
    val generation: Generation? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
