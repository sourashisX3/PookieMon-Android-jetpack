package com.funapp.pookiemon.feature.item.presentation.states

import com.funapp.pookiemon.feature.item.domain.model.Item

data class ItemDetailUiState(
    val item: Item? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
