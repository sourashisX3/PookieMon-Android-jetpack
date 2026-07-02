package com.funapp.pookiemon.feature.pokemon.presentation.states

import com.funapp.pookiemon.feature.pokemon.domain.model.Pokemon
import com.funapp.pookiemon.feature.pokemon.domain.model.PokemonSpecies

data class DetailUiState(
    val pokemon: Pokemon? = null,
    val species: PokemonSpecies? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
