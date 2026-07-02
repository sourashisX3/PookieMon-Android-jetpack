package com.funapp.pookiemon.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class StringValue(val value: String) : UiText()
    data class ResourceId(val id: Int, val args: List<Any> = emptyList()) : UiText()

    @Composable
    fun asString(): String = when (this) {
        is StringValue -> value
        is ResourceId -> stringResource(id, *args.toTypedArray())
    }
}
