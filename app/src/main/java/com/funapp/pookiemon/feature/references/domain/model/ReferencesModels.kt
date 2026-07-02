package com.funapp.pookiemon.feature.references.domain.model

data class Ability(
    val id: Int,
    val name: String,
    val isMainSeries: Boolean,
    val generation: String?,
)

data class PokemonType(
    val id: Int,
    val name: String,
    val doubleDamageTo: List<String>,
    val halfDamageTo: List<String>,
    val noDamageTo: List<String>,
    val doubleDamageFrom: List<String>,
    val halfDamageFrom: List<String>,
    val noDamageFrom: List<String>,
)

data class Nature(
    val id: Int,
    val name: String,
    val increasedStat: String?,
    val decreasedStat: String?,
    val likesFlavor: String?,
    val hatesFlavor: String?,
)
