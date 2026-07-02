package com.funapp.pookiemon.feature.home.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpeciesDto(
    val id: Int,
    val name: String,
    val order: Int = 0,
    val gender_rate: Int = -1,
    val capture_rate: Int = 0,
    val base_happiness: Int = 50,
    val is_baby: Boolean = false,
    val is_legendary: Boolean = false,
    val is_mythical: Boolean = false,
    val hatch_counter: Int = 0,
    val has_gender_differences: Boolean = false,
    val forms_switchable: Boolean = false,
    val growth_rate: NamedApiResourceDto? = null,
    val pokedex_numbers: List<PokemonSpeciesDexEntryDto> = emptyList(),
    val egg_groups: List<NamedApiResourceDto> = emptyList(),
    val color: NamedApiResourceDto? = null,
    val shape: NamedApiResourceDto? = null,
    val evolves_from_species: NamedApiResourceDto? = null,
    val evolution_chain: EvolutionChainDto? = null,
    val habitat: NamedApiResourceDto? = null,
    val generation: NamedApiResourceDto? = null,
    val names: List<PokemonNameDto> = emptyList(),
    val flavor_text_entries: List<FlavorTextEntryDto> = emptyList(),
    val form_descriptions: List<FormDescriptionDto> = emptyList(),
    val genera: List<GenusDto> = emptyList(),
    val varieties: List<PokemonVarietyDto> = emptyList(),
)

@Serializable
data class PokemonSpeciesDexEntryDto(
    val entry_number: Int,
    val pokedex: NamedApiResourceDto,
)

@Serializable
data class EvolutionChainDto(
    val url: String,
)

@Serializable
data class PokemonNameDto(
    val name: String,
    val language: NamedApiResourceDto,
)

@Serializable
data class FlavorTextEntryDto(
    val flavor_text: String,
    val language: NamedApiResourceDto,
    val version: NamedApiResourceDto? = null,
)

@Serializable
data class FormDescriptionDto(
    val description: String,
    val language: NamedApiResourceDto,
)

@Serializable
data class GenusDto(
    val genus: String,
    val language: NamedApiResourceDto,
)

@Serializable
data class PokemonVarietyDto(
    val is_default: Boolean,
    val pokemon: NamedApiResourceDto,
)
