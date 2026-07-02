package com.funapp.pookiemon.feature.item.presentation.events

sealed class ItemListUiEvent {
    data object LoadItems : ItemListUiEvent()
    data object LoadMoreItems : ItemListUiEvent()
    data class ItemClicked(val itemId: Int) : ItemListUiEvent()
    data object RetryClicked : ItemListUiEvent()
}
