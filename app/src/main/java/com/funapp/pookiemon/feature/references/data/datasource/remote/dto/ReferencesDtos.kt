package com.funapp.pookiemon.feature.references.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AbilityListResponseDto(
    val count: Int = 0,
    val results: List<NamedApiResource> = emptyList(),
)

@Serializable
data class AbilityDto(
    val id: Int = 0,
    val name: String = "",
    val is_main_series: Boolean = false,
    val generation: NamedApiResource? = null,
    val names: List<AbilityNameDto> = emptyList(),
    val flavor_text_entries: List<AbilityFlavorTextDto> = emptyList(),
)

@Serializable
data class AbilityNameDto(
    val name: String = "",
    val language: NamedApiResource? = null,
)

@Serializable
data class AbilityFlavorTextDto(
    val flavor_text: String = "",
    val language: NamedApiResource? = null,
    val version_group: NamedApiResource? = null,
)

@Serializable
data class TypeListResponseDto(
    val count: Int = 0,
    val results: List<NamedApiResource> = emptyList(),
)

@Serializable
data class TypeDto(
    val id: Int = 0,
    val name: String = "",
    val names: List<TypeNameDto> = emptyList(),
    val damage_relations: TypeRelationsDto? = null,
)

@Serializable
data class TypeNameDto(
    val name: String = "",
    val language: NamedApiResource? = null,
)

@Serializable
data class TypeRelationsDto(
    val no_damage_to: List<NamedApiResource> = emptyList(),
    val half_damage_to: List<NamedApiResource> = emptyList(),
    val double_damage_to: List<NamedApiResource> = emptyList(),
    val no_damage_from: List<NamedApiResource> = emptyList(),
    val half_damage_from: List<NamedApiResource> = emptyList(),
    val double_damage_from: List<NamedApiResource> = emptyList(),
)

@Serializable
data class NatureListResponseDto(
    val count: Int = 0,
    val results: List<NamedApiResource> = emptyList(),
)

@Serializable
data class NatureDto(
    val id: Int = 0,
    val name: String = "",
    val decreased_stat: NamedApiResource? = null,
    val increased_stat: NamedApiResource? = null,
    val likes_flavor: NamedApiResource? = null,
    val hates_flavor: NamedApiResource? = null,
    val names: List<NatureNameDto> = emptyList(),
)

@Serializable
data class NatureNameDto(
    val name: String = "",
    val language: NamedApiResource? = null,
)

@Serializable
data class NamedApiResource(
    val name: String = "",
    val url: String = "",
)
