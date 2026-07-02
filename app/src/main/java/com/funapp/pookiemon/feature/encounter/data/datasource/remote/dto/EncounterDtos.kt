package com.funapp.pookiemon.feature.encounter.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class EncounterMethodListResponseDto(
    val count: Int = 0,
    val results: List<NamedApiResource> = emptyList(),
)

@Serializable
data class EncounterMethodDto(
    val id: Int = 0,
    val name: String = "",
    val order: Int = 0,
    val names: List<EncounterNameDto> = emptyList(),
)

@Serializable
data class EncounterNameDto(
    val name: String = "",
    val language: NamedApiResource? = null,
)

@Serializable
data class NamedApiResource(
    val name: String = "",
    val url: String = "",
)
