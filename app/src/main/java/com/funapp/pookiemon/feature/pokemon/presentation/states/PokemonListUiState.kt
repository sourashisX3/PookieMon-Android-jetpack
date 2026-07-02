package com.funapp.pookiemon.feature.pokemon.presentation.states

import com.funapp.pookiemon.feature.pokemon.domain.model.PokemonListItem

data class PokemonListUiState(
    val pokemonList: List<PokemonListItem> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val hasMore: Boolean = true,
    val currentOffset: Int = 0,
)
