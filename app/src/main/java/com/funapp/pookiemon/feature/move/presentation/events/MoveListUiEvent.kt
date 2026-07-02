package com.funapp.pookiemon.feature.move.presentation.events

sealed class MoveListUiEvent {
    data object LoadMoves : MoveListUiEvent()
    data object LoadMoreMoves : MoveListUiEvent()
    data class MoveClicked(val moveId: Int) : MoveListUiEvent()
    data object RetryClicked : MoveListUiEvent()
}
