package com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonCriesDto(
    val latest: String? = null,
    val legacy: String? = null,
)
