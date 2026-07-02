package com.funapp.pookiemon.feature.home.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDto(
    val id: Int,
    val name: String,
    val base_experience: Int? = null,
    val height: Int,
    val weight: Int,
    val is_default: Boolean = true,
    val order: Int = 0,
    val abilities: List<PokemonAbilityDto> = emptyList(),
    val forms: List<NamedApiResourceDto> = emptyList(),
    val game_indices: List<VersionGameIndexDto> = emptyList(),
    val held_items: List<PokemonHeldItemDto> = emptyList(),
    val location_area_encounters: String? = null,
    val moves: List<PokemonMoveDto> = emptyList(),
    val species: NamedApiResourceDto,
    val sprites: PokemonSpritesDto? = null,
    val cries: PokemonCriesDto? = null,
    val stats: List<PokemonStatDto> = emptyList(),
    val types: List<PokemonTypeDto> = emptyList(),
    val past_types: List<PokemonTypePastDto> = emptyList(),
    val past_abilities: List<PokemonAbilityPastDto> = emptyList(),
)

@Serializable
data class VersionGameIndexDto(
    val game_index: Int,
    val version: NamedApiResourceDto,
)
