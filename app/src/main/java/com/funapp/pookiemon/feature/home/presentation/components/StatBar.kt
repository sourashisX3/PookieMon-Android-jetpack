package com.funapp.pookiemon.feature.home.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.funapp.pookiemon.core.theme.Spacing

@Composable
fun StatBar(
    statName: String,
    baseStat: Int,
    modifier: Modifier = Modifier,
    typeColor: Color = MaterialTheme.colorScheme.primary,
    maxStat: Int = 255,
) {
    val fraction = (baseStat.toFloat() / maxStat).coerceIn(0f, 1f)
    val animatedFraction by animateFloatAsState(
        targetValue = fraction,
        animationSpec = tween(durationMillis = 800),
        label = "statBar",
    )

    val barGradient = Brush.horizontalGradient(
        colors = listOf(
            typeColor,
            typeColor.copy(alpha = 0.6f),
        ),
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = statName.take(3).uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(32.dp),
        )

        Spacer(modifier = Modifier.width(Spacing.sm))

        Text(
            text = baseStat.toString(),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = typeColor,
            modifier = Modifier.width(28.dp),
        )

        Spacer(modifier = Modifier.width(Spacing.sm))

        Box(
            modifier = Modifier
                .weight(1f)
                .height(10.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedFraction)
                    .height(10.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(barGradient),
            )
        }
    }
}

@Composable
fun StatSection(
    stats: List<com.funapp.pookiemon.feature.home.domain.model.PokemonStat>,
    modifier: Modifier = Modifier,
    accentColor: Color = MaterialTheme.colorScheme.primary,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
        Text(
            text = "Base Stats",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface,
        )

        var total = 0
        stats.forEach { stat ->
            StatBar(
                statName = stat.name,
                baseStat = stat.baseStat,
                typeColor = accentColor,
            )
            total += stat.baseStat
        }

        Spacer(modifier = Modifier.height(Spacing.xs))

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "TOT",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.width(32.dp),
            )
            Spacer(modifier = Modifier.width(Spacing.sm))
            Text(
                text = total.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = accentColor,
            )
        }
    }
}
