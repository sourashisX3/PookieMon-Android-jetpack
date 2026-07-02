package com.funapp.pookiemon.feature.home.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonAbilityPastDto(
    val generation: NamedApiResourceDto,
    val abilities: List<PokemonAbilityDto>,
)
