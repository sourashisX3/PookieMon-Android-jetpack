package com.funapp.pookiemon.core.config.network

object ApiConstants {
    const val TIMEOUT_CONNECT = 30L
    const val TIMEOUT_READ = 30L
    const val TIMEOUT_WRITE = 30L

    const val SPRITE_BASE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
    const val POKEMON_PER_PAGE = 20

    // Pokemon
    const val POKEMON = "pokemon"
    const val POKEMON_SPECIES = "pokemon-species"

    // Item
    const val ITEM = "item"
    const val ITEM_CATEGORY = "item-category"

    // Move
    const val MOVE = "move"
    const val MOVE_DAMAGE_CLASS = "move-damage-class"

    // Berry
    const val BERRY = "berry"

    // Evolution
    const val EVOLUTION_CHAIN = "evolution-chain"

    // Location
    const val LOCATION = "location"

    // Encounter
    const val ENCOUNTER_METHOD = "encounter-method"

    // Games
    const val GENERATION = "generation"
    const val VERSION = "version"
    const val POKEDEX = "pokedex"

    // References
    const val ABILITY = "ability"
    const val TYPE = "type"
    const val NATURE = "nature"
}

fun pokemonArtworkUrl(id: Int): String =
    "${ApiConstants.SPRITE_BASE_URL}other/official-artwork/$id.png"

fun pokemonSpriteUrl(id: Int): String =
    "${ApiConstants.SPRITE_BASE_URL}$id.png"
