package com.funapp.pookiemon.feature.home.domain.model

data class PokemonSprites(
    val frontDefault: String,
    val frontShiny: String? = null,
    val artworkDefault: String? = null,
    val artworkShiny: String? = null,
)
