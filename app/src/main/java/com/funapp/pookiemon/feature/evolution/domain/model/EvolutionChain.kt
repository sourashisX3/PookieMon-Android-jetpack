package com.funapp.pookiemon.feature.evolution.domain.model

data class EvolutionChain(
    val id: Int,
    val species: String,
    val speciesId: Int,
    val evolvesTo: List<EvolutionNode>,
)

data class EvolutionNode(
    val species: String,
    val speciesId: Int,
    val minLevel: Int?,
    val trigger: String?,
    val item: String?,
    val heldItem: String?,
    val knownMove: String?,
    val evolvesTo: List<EvolutionNode>,
)
