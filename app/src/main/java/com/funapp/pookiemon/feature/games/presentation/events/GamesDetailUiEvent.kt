package com.funapp.pookiemon.feature.games.presentation.events

sealed class GamesDetailUiEvent {
    data object RetryClicked : GamesDetailUiEvent()
}
