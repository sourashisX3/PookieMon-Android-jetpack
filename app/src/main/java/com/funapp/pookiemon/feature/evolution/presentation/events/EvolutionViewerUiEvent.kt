package com.funapp.pookiemon.feature.evolution.presentation.events

sealed class EvolutionViewerUiEvent {
    data object RetryClicked : EvolutionViewerUiEvent()
}
