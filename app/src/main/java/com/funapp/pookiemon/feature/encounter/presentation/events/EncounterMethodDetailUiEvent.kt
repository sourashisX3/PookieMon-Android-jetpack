package com.funapp.pookiemon.feature.encounter.presentation.events

sealed class EncounterMethodDetailUiEvent {
    data object RetryClicked : EncounterMethodDetailUiEvent()
}
