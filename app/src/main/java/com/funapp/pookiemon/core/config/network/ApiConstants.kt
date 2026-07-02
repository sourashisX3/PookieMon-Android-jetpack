package com.funapp.pookiemon.core.config.network

object ApiConstants {
    const val TIMEOUT_CONNECT = 30L
    const val TIMEOUT_READ = 30L
    const val TIMEOUT_WRITE = 30L
    const val SPRITE_BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
    const val POKEMON_PER_PAGE = 20
}

fun pokemonArtworkUrl(id: Int): String =
    "${ApiConstants.SPRITE_BASE_URL}other/official-artwork/$id.png"

fun pokemonSpriteUrl(id: Int): String =
    "${ApiConstants.SPRITE_BASE_URL}$id.png"
