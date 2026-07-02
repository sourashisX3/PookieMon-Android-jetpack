package com.funapp.pookiemon.core.ui.components

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

private const val SHIMMER_WIDTH = 300f

@Composable
fun shimmerBrush(
    targetValue: Float,
    shimmerColor: Color,
    baseColor: Color,
): Brush {
    return Brush.linearGradient(
        colors = listOf(baseColor, shimmerColor, baseColor),
        start = Offset(targetValue, 0f),
        end = Offset(targetValue + SHIMMER_WIDTH, 0f),
    )
}

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
        initialValue = -SHIMMER_WIDTH,
        targetValue = 1500f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmerTranslate",
    )

    val isDark = isSystemInDarkTheme()
    val shimmerColor = if (isDark) Color.White.copy(alpha = 0.25f) else Color.White.copy(alpha = 0.5f)
    val baseColor = if (isDark) Color.White.copy(alpha = 0.06f) else Color.Black.copy(alpha = 0.04f)

    return shimmerBrush(
        targetValue = shimmerTranslate,
        shimmerColor = shimmerColor,
        baseColor = baseColor,
    )
}
