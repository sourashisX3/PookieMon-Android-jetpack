package com.funapp.pookiemon.feature.berry.presentation.events

sealed class BerryListUiEvent {
    data object LoadBerries : BerryListUiEvent()
    data object LoadMoreBerries : BerryListUiEvent()
    data class BerryClicked(val berryId: Int) : BerryListUiEvent()
    data object RetryClicked : BerryListUiEvent()
}
