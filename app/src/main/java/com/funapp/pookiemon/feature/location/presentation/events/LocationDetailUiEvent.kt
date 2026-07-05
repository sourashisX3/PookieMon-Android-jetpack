package com.funapp.pookiemon.feature.location.presentation.events

sealed class LocationDetailUiEvent {
    data object RetryClicked : LocationDetailUiEvent()
}
