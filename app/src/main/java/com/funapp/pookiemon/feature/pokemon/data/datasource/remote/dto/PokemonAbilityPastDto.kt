package com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonAbilityPastDto(
    val generation: NamedApiResourceDto,
    val abilities: List<PokemonAbilityDto>,
)
