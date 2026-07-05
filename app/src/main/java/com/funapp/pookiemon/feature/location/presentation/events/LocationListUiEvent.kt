package com.funapp.pookiemon.feature.location.presentation.events

sealed class LocationListUiEvent {
    data object LoadLocations : LocationListUiEvent()
    data object LoadMoreLocations : LocationListUiEvent()
    data class LocationClicked(val locationId: Int) : LocationListUiEvent()
    data object RetryClicked : LocationListUiEvent()
}
