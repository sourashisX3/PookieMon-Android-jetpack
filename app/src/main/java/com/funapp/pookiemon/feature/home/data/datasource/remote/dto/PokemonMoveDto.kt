package com.funapp.pookiemon.feature.home.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonMoveDto(
    val move: NamedApiResourceDto,
    val version_group_details: List<PokemonMoveVersionDto>,
)

@Serializable
data class PokemonMoveVersionDto(
    val level_learned_at: Int,
    val move_learn_method: NamedApiResourceDto,
    val version_group: NamedApiResourceDto,
)
