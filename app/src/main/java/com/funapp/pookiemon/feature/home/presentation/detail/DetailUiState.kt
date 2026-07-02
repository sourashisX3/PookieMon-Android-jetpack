package com.funapp.pookiemon.feature.home.presentation.detail

import com.funapp.pookiemon.feature.home.domain.model.Pokemon
import com.funapp.pookiemon.feature.home.domain.model.PokemonSpecies

data class DetailUiState(
    val pokemon: Pokemon? = null,
    val species: PokemonSpecies? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
