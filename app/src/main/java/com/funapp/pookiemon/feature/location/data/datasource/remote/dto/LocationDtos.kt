package com.funapp.pookiemon.feature.location.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationListResponseDto(
    val count: Int = 0,
    val results: List<NamedApiResource> = emptyList(),
)

@Serializable
data class LocationDto(
    val id: Int = 0,
    val name: String = "",
    val region: NamedApiResource? = null,
    val areas: List<NamedApiResource> = emptyList(),
)

@Serializable
data class NamedApiResource(
    val name: String = "",
    val url: String = "",
)
