package com.funapp.pookiemon.feature.move.presentation.states

import com.funapp.pookiemon.feature.move.domain.model.Move

data class MoveDetailUiState(
    val move: Move? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
