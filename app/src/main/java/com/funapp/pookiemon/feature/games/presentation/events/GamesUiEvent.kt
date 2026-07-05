package com.funapp.pookiemon.feature.games.presentation.events

sealed class GamesUiEvent {
    data object LoadData : GamesUiEvent()
    data object RetryClicked : GamesUiEvent()
}
