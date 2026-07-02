package com.funapp.pookiemon.core.config.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object PookieMonRoute : Route

    @Serializable
    data object HomeRoute : Route

    @Serializable
    data class PokemonDetailRoute(val pokemonId: Int) : Route
}
