package com.funapp.pookiemon.feature.references.presentation.states

import com.funapp.pookiemon.feature.references.domain.model.Ability
import com.funapp.pookiemon.feature.references.domain.model.Nature
import com.funapp.pookiemon.feature.references.domain.model.PokemonType

data class ReferencesUiState(
    val abilities: List<Ability> = emptyList(),
    val types: List<PokemonType> = emptyList(),
    val natures: List<Nature> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
