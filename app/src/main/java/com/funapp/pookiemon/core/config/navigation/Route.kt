package com.funapp.pookiemon.core.config.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object PookieMonRoute : Route

    @Serializable
    data object PokemonListRoute : Route

    @Serializable
    data class PokemonDetailRoute(val pokemonId: Int) : Route

    @Serializable
    data object ItemListRoute : Route

    @Serializable
    data class ItemDetailRoute(val itemId: Int) : Route

    @Serializable
    data object MoveListRoute : Route

    @Serializable
    data class MoveDetailRoute(val moveId: Int) : Route

    @Serializable
    data object ExploreRoute : Route

    @Serializable
    data class BerryListRoute(val category: String = "berries") : Route

    @Serializable
    data class BerryDetailRoute(val berryId: Int) : Route

    @Serializable
    data class LocationListRoute(val category: String = "locations") : Route

    @Serializable
    data class EvolutionViewerRoute(val speciesId: Int = 1) : Route

    @Serializable
    data class EncounterListRoute(val category: String = "encounters") : Route

    @Serializable
    data class EncounterDetailRoute(val methodId: Int) : Route

    @Serializable
    data class LocationDetailRoute(val locationId: Int) : Route

    @Serializable
    data object GamesListRoute : Route

    @Serializable
    data class GenerationDetailRoute(val genId: Int) : Route

    @Serializable
    data object ReferencesRoute : Route

    @Serializable
    data class AbilityDetailRoute(val abilityId: Int) : Route

    @Serializable
    data class TypeDetailRoute(val typeId: Int) : Route

    @Serializable
    data class NatureDetailRoute(val natureId: Int) : Route

    @Serializable
    data object SettingsRoute : Route
}

enum class BottomNavTab(
    val label: String,
    val route: Route,
) {
    Pokemon("tab_pokemon", Route.PokemonListRoute),
    Items("tab_items", Route.ItemListRoute),
    Moves("tab_moves", Route.MoveListRoute),
    Explore("tab_explore", Route.ExploreRoute),
    Settings("tab_settings", Route.SettingsRoute),
}
