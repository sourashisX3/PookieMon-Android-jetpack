package com.funapp.pookiemon.feature.evolution.presentation.states

import com.funapp.pookiemon.feature.evolution.domain.model.EvolutionChain

data class EvolutionViewerUiState(
    val chain: EvolutionChain? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
