package com.funapp.pookiemon.feature.home.domain.model

data class PokemonSpecies(
    val id: Int,
    val name: String,
    val flavorText: String?,
    val genus: String?,
    val color: String?,
    val habitat: String?,
    val isLegendary: Boolean,
    val isMythical: Boolean,
    val evolutionChainId: Int?,
    val evolvesFromSpecies: String?,
)
