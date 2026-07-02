package com.funapp.pookiemon.feature.home.presentation.list

import com.funapp.pookiemon.feature.home.domain.model.PokemonListItem

data class HomeUiState(
    val pokemonList: List<PokemonListItem> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val hasMore: Boolean = true,
    val currentOffset: Int = 0,
)
