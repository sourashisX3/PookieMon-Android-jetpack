package com.funapp.pookiemon.feature.references.presentation.events

sealed class RefDetailUiEvent {
    data object RetryClicked : RefDetailUiEvent()
}
