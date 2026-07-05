package com.funapp.pookiemon.feature.references.presentation.events

sealed class ReferencesUiEvent {
    data object LoadData : ReferencesUiEvent()
    data object RetryClicked : ReferencesUiEvent()
}
