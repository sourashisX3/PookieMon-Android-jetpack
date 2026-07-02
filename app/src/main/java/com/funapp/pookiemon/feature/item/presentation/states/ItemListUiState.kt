package com.funapp.pookiemon.feature.item.presentation.states

import com.funapp.pookiemon.feature.item.domain.model.Item

data class ItemListUiState(
    val items: List<Item> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val hasMore: Boolean = true,
    val currentOffset: Int = 0,
)
