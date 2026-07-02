package com.funapp.pookiemon.feature.pokemon.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponseDto(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<NamedApiResourceDto>,
)
