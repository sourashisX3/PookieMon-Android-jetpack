package com.funapp.pookiemon.feature.berry.presentation.events

sealed class BerryDetailUiEvent {
    data object RetryClicked : BerryDetailUiEvent()
}
