package com.funapp.pookiemon.core.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun shimmerBrush(
    targetValue: Float,
    shimmerColor: Color = Color.White.copy(alpha = 0.3f),
    baseColor: Color = Color.White.copy(alpha = 0.1f),
): Brush {
    return Brush.linearGradient(
        colors = listOf(baseColor, baseColor, shimmerColor, baseColor, baseColor),
        start = Offset.Zero,
        end = Offset(x = targetValue, y = targetValue),
    )
}

@SuppressLint("SuspiciousModifierThen")
fun Modifier.shimmerEffect(
    brush: Brush,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
): Modifier = this.then(
    clip(shape).background(brush),
)

@Composable
fun rememberShimmerBrush(): Brush {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val shimmerTranslate by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmerTranslate",
    )

    val isDark = isSystemInDarkTheme()
    val shimmerColor = if (isDark) Color.White.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.12f)
    val baseColor = if (isDark) Color.White.copy(alpha = 0.1f) else Color.Black.copy(alpha = 0.04f)

    return shimmerBrush(
        targetValue = shimmerTranslate,
        shimmerColor = shimmerColor,
        baseColor = baseColor,
    )
}
