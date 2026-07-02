package com.funapp.pookiemon.feature.move.presentation.events

sealed class MoveDetailUiEvent {
    data object RetryClicked : MoveDetailUiEvent()
}
