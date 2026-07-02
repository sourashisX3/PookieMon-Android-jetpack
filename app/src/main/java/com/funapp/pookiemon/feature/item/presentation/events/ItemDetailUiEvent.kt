package com.funapp.pookiemon.feature.item.presentation.events

sealed class ItemDetailUiEvent {
    data object RetryClicked : ItemDetailUiEvent()
}
