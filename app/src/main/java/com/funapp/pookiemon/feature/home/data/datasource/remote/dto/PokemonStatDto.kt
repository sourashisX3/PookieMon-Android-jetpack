package com.funapp.pookiemon.feature.home.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonStatDto(
    val base_stat: Int,
    val effort: Int,
    val stat: NamedApiResourceDto,
)
