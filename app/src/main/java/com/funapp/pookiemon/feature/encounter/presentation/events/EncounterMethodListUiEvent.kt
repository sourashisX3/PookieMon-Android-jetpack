package com.funapp.pookiemon.feature.encounter.presentation.events

sealed class EncounterMethodListUiEvent {
    data object LoadMethods : EncounterMethodListUiEvent()
    data object LoadMoreMethods : EncounterMethodListUiEvent()
    data class MethodClicked(val methodId: Int) : EncounterMethodListUiEvent()
    data object RetryClicked : EncounterMethodListUiEvent()
}
