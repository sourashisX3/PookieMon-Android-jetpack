package com.funapp.pookiemon.feature.move.presentation.states

import com.funapp.pookiemon.feature.move.domain.model.Move

data class MoveListUiState(
    val moves: List<Move> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val hasMore: Boolean = true,
    val currentOffset: Int = 0,
)
