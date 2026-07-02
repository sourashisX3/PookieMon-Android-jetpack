package com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypeDto(
    val slot: Int,
    val type: NamedApiResourceDto,
)
