package com.funapp.pookiemon.core.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import com.funapp.pookiemon.core.utils.performClickHaptic
import androidx.compose.ui.unit.dp
import com.funapp.pookiemon.core.theme.ElevationLevels
import com.funapp.pookiemon.core.theme.ShapeTokens
import com.funapp.pookiemon.core.theme.Spacing

enum class CardVariant {
    ELEVATED,
    FILLED,
    OUTLINED,
}

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    variant: CardVariant = CardVariant.ELEVATED,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val shape = ShapeTokens.card
    val view = LocalView.current

    val wrappedOnClick = onClick?.let {
        {
            view.performClickHaptic()
            it()
        }
    }

    when (variant) {
        CardVariant.ELEVATED -> {
            Card(
                modifier = modifier.then(
                    if (wrappedOnClick != null) Modifier.clickable(onClick = wrappedOnClick) else Modifier,
                ),
                shape = shape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = ElevationLevels.subtle,
                    pressedElevation = ElevationLevels.light,
                ),
            ) {
                content()
            }
        }

        CardVariant.FILLED -> {
            Box(
                modifier = modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = shape,
                    )
                    .then(
                        if (wrappedOnClick != null) Modifier.clickable(onClick = wrappedOnClick) else Modifier,
                    )
                    .padding(Spacing.md),
            ) {
                content()
            }
        }

        CardVariant.OUTLINED -> {
            Box(
                modifier = modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = shape,
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = shape,
                    )
                    .then(
                        if (wrappedOnClick != null) Modifier.clickable(onClick = wrappedOnClick) else Modifier,
                    )
                    .padding(Spacing.md),
            ) {
                content()
            }
        }
    }
}

@Composable
fun ElevatedCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    AppCard(
        modifier = modifier,
        variant = CardVariant.ELEVATED,
        onClick = onClick,
        content = content,
    )
}

@Composable
fun FilledCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    AppCard(
        modifier = modifier,
        variant = CardVariant.FILLED,
        onClick = onClick,
        content = content,
    )
}

@Composable
fun OutlinedCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    AppCard(
        modifier = modifier,
        variant = CardVariant.OUTLINED,
        onClick = onClick,
        content = content,
    )
}
