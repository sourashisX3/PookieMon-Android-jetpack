package com.funapp.pookiemon.feature.pokemon.domain.model

data class PokemonListItem(
    val id: Int,
    val name: String,
    val spriteUrl: String?,
    val types: List<PokemonType>,
)
