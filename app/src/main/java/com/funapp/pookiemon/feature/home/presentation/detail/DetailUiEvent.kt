package com.funapp.pookiemon.feature.home.presentation.detail

sealed class DetailUiEvent {
    data class LoadDetail(val pokemonId: Int) : DetailUiEvent()
    data object RetryClicked : DetailUiEvent()
    data object NavigateBack : DetailUiEvent()
}
