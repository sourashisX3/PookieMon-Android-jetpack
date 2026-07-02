package com.funapp.pookiemon.feature.home.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpritesDto(
    val front_default: String? = null,
    val front_shiny: String? = null,
    val front_female: String? = null,
    val front_shiny_female: String? = null,
    val back_default: String? = null,
    val back_shiny: String? = null,
    val back_female: String? = null,
    val back_shiny_female: String? = null,
    val other: OtherSpritesDto? = null,
)

@Serializable
data class OtherSpritesDto(
    @SerialName("dream_world")
    val dreamWorld: DreamWorldSpritesDto? = null,
    val home: HomeSpritesDto? = null,
    @SerialName("official-artwork")
    val officialArtwork: OfficialArtworkSpritesDto? = null,
)

@Serializable
data class DreamWorldSpritesDto(
    val front_default: String? = null,
    val front_female: String? = null,
)

@Serializable
data class HomeSpritesDto(
    val front_default: String? = null,
    val front_female: String? = null,
    val front_shiny: String? = null,
    val front_shiny_female: String? = null,
)

@Serializable
data class OfficialArtworkSpritesDto(
    val front_default: String? = null,
    val front_shiny: String? = null,
)
