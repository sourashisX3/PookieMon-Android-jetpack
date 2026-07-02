package com.funapp.pookiemon.feature.games.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenerationListResponseDto(
    val count: Int = 0,
    val results: List<NamedApiResource> = emptyList(),
)

@Serializable
data class GenerationDto(
    val id: Int = 0,
    val name: String = "",
    val main_region: NamedApiResource? = null,
    val versions: List<NamedApiResource> = emptyList(),
    val pokemon_species: List<NamedApiResource> = emptyList(),
)

@Serializable
data class VersionListResponseDto(
    val count: Int = 0,
    val results: List<NamedApiResource> = emptyList(),
)

@Serializable
data class VersionDto(
    val id: Int = 0,
    val name: String = "",
    val version_group: NamedApiResource? = null,
)

@Serializable
data class VersionGroupListResponseDto(
    val count: Int = 0,
    val results: List<NamedApiResource> = emptyList(),
)

@Serializable
data class VersionGroupDto(
    val id: Int = 0,
    val name: String = "",
    val generation: NamedApiResource? = null,
    val versions: List<NamedApiResource> = emptyList(),
    val pokedexes: List<NamedApiResource> = emptyList(),
)

@Serializable
data class PokedexListResponseDto(
    val count: Int = 0,
    val results: List<NamedApiResource> = emptyList(),
)

@Serializable
data class PokedexDto(
    val id: Int = 0,
    val name: String = "",
    val is_main_series: Boolean = false,
    val region: NamedApiResource? = null,
    val version_groups: List<NamedApiResource> = emptyList(),
    val pokemon_entries: List<PokedexEntryDto> = emptyList(),
)

@Serializable
data class PokedexEntryDto(
    val entry_number: Int = 0,
    val pokemon_species: NamedApiResource? = null,
)

@Serializable
data class NamedApiResource(
    val name: String = "",
    val url: String = "",
)
