package com.funapp.pookiemon.feature.games.presentation.states

import com.funapp.pookiemon.feature.games.domain.model.Generation
import com.funapp.pookiemon.feature.games.domain.model.Pokedex
import com.funapp.pookiemon.feature.games.domain.model.Version

data class GamesUiState(
    val generations: List<Generation> = emptyList(),
    val versions: List<Version> = emptyList(),
    val pokedexes: List<Pokedex> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
