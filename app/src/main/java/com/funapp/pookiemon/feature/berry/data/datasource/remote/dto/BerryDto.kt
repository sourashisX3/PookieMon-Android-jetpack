package com.funapp.pookiemon.feature.berry.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BerryDto(
    val id: Int,
    val name: String,
    val growth_time: Int,
    val max_harvest: Int,
    val natural_gift_power: Int,
    val size: Int,
    val smoothness: Int,
    val soil_dryness: Int,
    val firmness: NamedApiResource,
    val flavors: List<BerryFlavorDto> = emptyList(),
    val item: NamedApiResource,
    val natural_gift_type: NamedApiResource? = null,
)

@Serializable
data class BerryFlavorDto(
    val potency: Int,
    val flavor: NamedApiResource,
)

@Serializable
data class BerryListResponseDto(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<NamedApiResource>,
)

@Serializable
data class NamedApiResource(
    val name: String,
    val url: String,
)
