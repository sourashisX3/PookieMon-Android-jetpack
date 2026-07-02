package com.funapp.pookiemon.core.utils

sealed class UiEvent {
    data object NavigateBack : UiEvent()
    data class Navigate(val route: String) : UiEvent()
    data class ShowSnackbar(val message: UiText, val action: UiText? = null) : UiEvent()
    data class ShowToast(val message: UiText) : UiEvent()
}
