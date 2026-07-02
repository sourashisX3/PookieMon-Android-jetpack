package com.funapp.pookiemon.feature.pokemon.presentation.events

sealed class PokemonListUiEvent {
    data object LoadPokemon : PokemonListUiEvent()
    data object LoadMorePokemon : PokemonListUiEvent()
    data class PokemonClicked(val pokemonId: Int) : PokemonListUiEvent()
    data object RetryClicked : PokemonListUiEvent()
}
