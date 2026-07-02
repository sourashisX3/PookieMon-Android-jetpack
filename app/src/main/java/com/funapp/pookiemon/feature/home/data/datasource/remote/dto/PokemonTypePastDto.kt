package com.funapp.pookiemon.feature.home.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypePastDto(
    val generation: NamedApiResourceDto,
    val types: List<PokemonTypeDto>,
)
