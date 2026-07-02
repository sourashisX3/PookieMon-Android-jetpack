package com.funapp.pookiemon.feature.games.domain.model

data class Generation(
    val id: Int,
    val name: String,
    val mainRegion: String?,
    val versions: List<String>,
    val pokemonSpecies: List<String>,
)

data class Version(
    val id: Int,
    val name: String,
    val versionGroup: String?,
)

data class Pokedex(
    val id: Int,
    val name: String,
    val isMainSeries: Boolean,
    val region: String?,
    val description: String = "",
)
