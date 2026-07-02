package com.funapp.pookiemon.core.data.settings

import androidx.compose.ui.graphics.Color
import com.funapp.pookiemon.core.theme.PookieMonColors
import com.funapp.pookiemon.core.theme.darkPookieMonColors
import com.funapp.pookiemon.core.theme.lightPookieMonColors

data class PaletteDefinition(
    val name: String,
    val displayName: String,
    val accentColor: Color,
    val lightColors: PookieMonColors,
    val darkColors: PookieMonColors,
)

object ColorPalettes {

    private val fireLight = lightPookieMonColors().copy(
        primary = Color(0xFFE53935),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFFFCDD2),
        onPrimaryContainer = Color(0xFFB71C1C),
        secondary = Color(0xFFFF7043),
        tertiary = Color(0xFFFFB300),
        error = Color(0xFFD32F2F),
    )

    private val fireDark = darkPookieMonColors().copy(
        primary = Color(0xFFEF9A9A),
        onPrimary = Color(0xFFB71C1C),
        primaryContainer = Color(0xFFC62828),
        onPrimaryContainer = Color(0xFFFFCDD2),
        secondary = Color(0xFFFF8A65),
        tertiary = Color(0xFFFFCA28),
    )

    private val waterLight = lightPookieMonColors().copy(
        primary = Color(0xFF1E88E5),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFBBDEFB),
        onPrimaryContainer = Color(0xFF0D47A1),
        secondary = Color(0xFF42A5F5),
        tertiary = Color(0xFF00ACC1),
        error = Color(0xFFD32F2F),
    )

    private val waterDark = darkPookieMonColors().copy(
        primary = Color(0xFF90CAF9),
        onPrimary = Color(0xFF0D47A1),
        primaryContainer = Color(0xFF1565C0),
        onPrimaryContainer = Color(0xFFBBDEFB),
        secondary = Color(0xFF64B5F6),
        tertiary = Color(0xFF26C6DA),
    )

    private val grassLight = lightPookieMonColors().copy(
        primary = Color(0xFF43A047),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFC8E6C9),
        onPrimaryContainer = Color(0xFF1B5E20),
        secondary = Color(0xFF66BB6A),
        tertiary = Color(0xFF8BC34A),
    )

    private val grassDark = darkPookieMonColors().copy(
        primary = Color(0xFFA5D6A7),
        onPrimary = Color(0xFF1B5E20),
        primaryContainer = Color(0xFF2E7D32),
        onPrimaryContainer = Color(0xFFC8E6C9),
        secondary = Color(0xFF81C784),
        tertiary = Color(0xFF9CCC65),
    )

    private val electricLight = lightPookieMonColors().copy(
        primary = Color(0xFFF9A825),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFFFF9C4),
        onPrimaryContainer = Color(0xFFF57F17),
        secondary = Color(0xFFFDD835),
        tertiary = Color(0xFFFFEE58),
    )

    private val electricDark = darkPookieMonColors().copy(
        primary = Color(0xFFE6EE9C),
        onPrimary = Color(0xFFF57F17),
        primaryContainer = Color(0xFFF9A825),
        onPrimaryContainer = Color(0xFFFFF9C4),
        secondary = Color(0xFFFDD835),
        tertiary = Color(0xFFFFEE58),
    )

    private val psychicLight = lightPookieMonColors().copy(
        primary = Color(0xFFE91E63),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFFCE4EC),
        onPrimaryContainer = Color(0xFF880E4F),
        secondary = Color(0xFFF06292),
        tertiary = Color(0xFFBA68C8),
    )

    private val psychicDark = darkPookieMonColors().copy(
        primary = Color(0xFFF48FB1),
        onPrimary = Color(0xFF880E4F),
        primaryContainer = Color(0xFFAD1457),
        onPrimaryContainer = Color(0xFFFCE4EC),
        secondary = Color(0xFFF06292),
        tertiary = Color(0xFFBA68C8),
    )

    private val darkPaletteLight = lightPookieMonColors().copy(
        primary = Color(0xFF5D4037),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFD7CCC8),
        onPrimaryContainer = Color(0xFF3E2723),
        secondary = Color(0xFF795548),
        tertiary = Color(0xFFA1887F),
    )

    private val darkPaletteDark = darkPookieMonColors().copy(
        primary = Color(0xFFBCAAA4),
        onPrimary = Color(0xFF3E2723),
        primaryContainer = Color(0xFF4E342E),
        onPrimaryContainer = Color(0xFFD7CCC8),
        secondary = Color(0xFF8D6E63),
        tertiary = Color(0xFFA1887F),
    )

    private val iceLight = lightPookieMonColors().copy(
        primary = Color(0xFF00ACC1),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFB2EBF2),
        onPrimaryContainer = Color(0xFF006064),
        secondary = Color(0xFF26C6DA),
        tertiary = Color(0xFF80DEEA),
    )

    private val iceDark = darkPookieMonColors().copy(
        primary = Color(0xFF80DEEA),
        onPrimary = Color(0xFF006064),
        primaryContainer = Color(0xFF00838F),
        onPrimaryContainer = Color(0xFFB2EBF2),
        secondary = Color(0xFF4DD0E1),
        tertiary = Color(0xFF80DEEA),
    )

    val all = listOf(
        PaletteDefinition(
            name = "default",
            displayName = "Default",
            accentColor = Color(0xFF6750A4),
            lightColors = lightPookieMonColors(),
            darkColors = darkPookieMonColors(),
        ),
        PaletteDefinition(
            name = "fire",
            displayName = "Fire",
            accentColor = Color(0xFFE53935),
            lightColors = fireLight,
            darkColors = fireDark,
        ),
        PaletteDefinition(
            name = "water",
            displayName = "Water",
            accentColor = Color(0xFF1E88E5),
            lightColors = waterLight,
            darkColors = waterDark,
        ),
        PaletteDefinition(
            name = "grass",
            displayName = "Grass",
            accentColor = Color(0xFF43A047),
            lightColors = grassLight,
            darkColors = grassDark,
        ),
        PaletteDefinition(
            name = "electric",
            displayName = "Electric",
            accentColor = Color(0xFFF9A825),
            lightColors = electricLight,
            darkColors = electricDark,
        ),
        PaletteDefinition(
            name = "psychic",
            displayName = "Psychic",
            accentColor = Color(0xFFE91E63),
            lightColors = psychicLight,
            darkColors = psychicDark,
        ),
        PaletteDefinition(
            name = "dark",
            displayName = "Dark",
            accentColor = Color(0xFF5D4037),
            lightColors = darkPaletteLight,
            darkColors = darkPaletteDark,
        ),
        PaletteDefinition(
            name = "ice",
            displayName = "Ice",
            accentColor = Color(0xFF00ACC1),
            lightColors = iceLight,
            darkColors = iceDark,
        ),
    )

    fun byName(name: String): PaletteDefinition =
        all.firstOrNull { it.name == name } ?: all.first()
}
