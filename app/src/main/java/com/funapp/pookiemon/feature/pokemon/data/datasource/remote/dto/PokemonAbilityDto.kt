package com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonAbilityDto(
    val is_hidden: Boolean = false,
    val slot: Int = 1,
    val ability: NamedApiResourceDto? = null,
)
