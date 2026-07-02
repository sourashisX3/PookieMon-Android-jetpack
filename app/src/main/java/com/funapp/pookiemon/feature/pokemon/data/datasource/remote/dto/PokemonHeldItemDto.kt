package com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonHeldItemDto(
    val item: NamedApiResourceDto,
    val version_details: List<PokemonHeldItemVersionDto>,
)

@Serializable
data class PokemonHeldItemVersionDto(
    val rarity: Int,
    val version: NamedApiResourceDto,
)
