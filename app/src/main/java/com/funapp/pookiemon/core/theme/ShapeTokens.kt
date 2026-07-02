package com.funapp.pookiemon.core.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object ShapeTokens {
    val extraSmall = RoundedCornerShape(4.dp)
    val small = RoundedCornerShape(8.dp)
    val medium = RoundedCornerShape(12.dp)
    val large = RoundedCornerShape(16.dp)
    val extraLarge = RoundedCornerShape(24.dp)
    val full = RoundedCornerShape(50)

    val button = medium
    val card = large
    val textField = small
    val dialog = large
    val bottomSheet = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    val chip = full
    val checkbox = extraSmall
}
