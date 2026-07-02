package com.funapp.pookiemon.feature.home.presentation.list

sealed class HomeUiEvent {
    data object LoadPokemon : HomeUiEvent()
    data object LoadMorePokemon : HomeUiEvent()
    data class PokemonClicked(val pokemonId: Int) : HomeUiEvent()
    data object RetryClicked : HomeUiEvent()
}
