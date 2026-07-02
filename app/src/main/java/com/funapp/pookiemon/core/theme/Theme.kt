package com.funapp.pookiemon.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalPookieMonColors = staticCompositionLocalOf { lightPookieMonColors() }

@Composable
fun PookieMonTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val pookieMonColors = if (darkTheme) darkPookieMonColors() else lightPookieMonColors()

    val colorScheme = if (darkTheme) {
        pookieMonColors.toDarkColorScheme()
    } else {
        pookieMonColors.toLightColorScheme()
    }

    CompositionLocalProvider(LocalPookieMonColors provides pookieMonColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = PookieMonTypography,
            content = content,
        )
    }
}

object PookieMonTheme {
    val colors: PookieMonColors
        @Composable get() = LocalPookieMonColors.current
}
