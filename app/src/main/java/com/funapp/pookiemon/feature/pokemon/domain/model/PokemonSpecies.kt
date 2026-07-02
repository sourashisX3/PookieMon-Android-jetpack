package com.funapp.pookiemon.feature.pokemon.domain.model

data class PokemonSpecies(
    val id: Int,
    val name: String,
    val flavorText: String?,
    val genus: String?,
    val color: String?,
    val habitat: String?,
    val isLegendary: Boolean,
    val isMythical: Boolean,
    val isBaby: Boolean,
    val hasGenderDifferences: Boolean,
    val evolutionChainId: Int?,
    val evolvesFromSpecies: String?,
    val eggGroups: List<String>,
    val growthRate: String?,
    val generation: String?,
    val captureRate: Int?,
    val baseHappiness: Int?,
    val hatchCounter: Int?,
    val shape: String?,
)
