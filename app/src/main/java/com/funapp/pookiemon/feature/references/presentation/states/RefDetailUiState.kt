package com.funapp.pookiemon.feature.references.presentation.states

import com.funapp.pookiemon.feature.references.domain.model.Ability
import com.funapp.pookiemon.feature.references.domain.model.Nature
import com.funapp.pookiemon.feature.references.domain.model.PokemonType

data class RefDetailUiState(
    val ability: Ability? = null,
    val type: PokemonType? = null,
    val nature: Nature? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
