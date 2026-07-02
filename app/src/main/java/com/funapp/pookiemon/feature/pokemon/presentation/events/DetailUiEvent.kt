package com.funapp.pookiemon.feature.pokemon.presentation.events

sealed class DetailUiEvent {
    data class LoadDetail(val pokemonId: Int) : DetailUiEvent()
    data object RetryClicked : DetailUiEvent()
    data object NavigateBack : DetailUiEvent()
}
