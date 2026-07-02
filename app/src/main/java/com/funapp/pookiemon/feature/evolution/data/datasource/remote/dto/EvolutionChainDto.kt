package com.funapp.pookiemon.feature.evolution.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class EvolutionChainResponseDto(
    val id: Int,
    val baby_trigger_item: NamedApiResource? = null,
    val chain: ChainLinkDto,
)

@Serializable
data class ChainLinkDto(
    val is_baby: Boolean = false,
    val species: NamedApiResource,
    val evolution_details: List<EvolutionDetailDto>? = null,
    val evolves_to: List<ChainLinkDto> = emptyList(),
)

@Serializable
data class EvolutionDetailDto(
    val item: NamedApiResource? = null,
    val trigger: NamedApiResource? = null,
    val gender: Int? = null,
    val held_item: NamedApiResource? = null,
    val known_move: NamedApiResource? = null,
    val known_move_type: NamedApiResource? = null,
    val location: NamedApiResource? = null,
    val min_level: Int? = null,
    val min_happiness: Int? = null,
    val min_beauty: Int? = null,
    val min_affection: Int? = null,
    val time_of_day: String = "",
    val trade_species: NamedApiResource? = null,
    val turn_upside_down: Boolean = false,
)

@Serializable
data class NamedApiResource(
    val name: String,
    val url: String,
)
