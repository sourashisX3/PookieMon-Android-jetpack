package com.funapp.pookiemon.feature.move.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MoveDto(
    val id: Int,
    val name: String,
    val accuracy: Int? = null,
    val effect_chance: Int? = null,
    val pp: Int? = null,
    val priority: Int = 0,
    val power: Int? = null,
    val effect_entries: List<MoveEffectEntryDto> = emptyList(),
    val flavor_text_entries: List<MoveFlavorTextDto> = emptyList(),
    val type: NamedApiResource? = null,
    val damage_class: NamedApiResource? = null,
    val target: NamedApiResource? = null,
    val contest_type: NamedApiResource? = null,
    val contest_effect: ApiResource? = null,
    val super_contest_effect: ApiResource? = null,
    val generation: NamedApiResource? = null,
    val names: List<MoveNameDto> = emptyList(),
    val machines: List<MachineVersionDto> = emptyList(),
    val stat_changes: List<MoveStatChangeDto> = emptyList(),
)

@Serializable
data class MoveEffectEntryDto(
    val effect: String,
    val short_effect: String,
    val language: NamedApiResource,
)

@Serializable
data class MoveFlavorTextDto(
    val flavor_text: String,
    val language: NamedApiResource,
    val version_group: NamedApiResource? = null,
)

@Serializable
data class MoveNameDto(
    val name: String,
    val language: NamedApiResource,
)

@Serializable
data class MachineVersionDto(
    val machine: ApiResource,
    val version_group: NamedApiResource,
)

@Serializable
data class MoveStatChangeDto(
    val change: Int,
    val stat: NamedApiResource,
)

@Serializable
data class MoveListResponseDto(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<NamedApiResource>,
)

@Serializable
data class MoveDamageClassDto(
    val id: Int,
    val name: String,
    val descriptions: List<DamageClassDescriptionDto> = emptyList(),
    val names: List<MoveNameDto> = emptyList(),
    val moves: List<NamedApiResource> = emptyList(),
)

@Serializable
data class DamageClassDescriptionDto(
    val description: String,
    val language: NamedApiResource,
)

@Serializable
data class NamedApiResource(
    val name: String,
    val url: String,
)

@Serializable
data class ApiResource(
    val url: String,
)
