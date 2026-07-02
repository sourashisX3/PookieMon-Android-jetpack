package com.funapp.pookiemon.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.funapp.pookiemon.core.data.settings.ColorPalettes

val LocalPookieMonColors = staticCompositionLocalOf { lightPookieMonColors() }

@Composable
fun PookieMonTheme(
    paletteName: String = "default",
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val paletteDefinition = ColorPalettes.byName(paletteName)
    val pookieMonColors = if (darkTheme) paletteDefinition.darkColors else paletteDefinition.lightColors

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
