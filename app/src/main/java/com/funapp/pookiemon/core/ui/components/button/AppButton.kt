package com.funapp.pookiemon.core.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import com.funapp.pookiemon.core.utils.performClickHaptic
import androidx.compose.ui.unit.dp
import com.funapp.pookiemon.core.theme.ComponentSize
import com.funapp.pookiemon.core.theme.ShapeTokens
import com.funapp.pookiemon.core.theme.Spacing
import com.funapp.pookiemon.core.utils.UiText

enum class ButtonVariant {
    PRIMARY,
    SECONDARY,
    TERTIARY,
    DANGER,
    SUCCESS,
}

enum class ButtonSize {
    SMALL,
    MEDIUM,
    LARGE,
}

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String? = null,
    variant: ButtonVariant = ButtonVariant.PRIMARY,
    size: ButtonSize = ButtonSize.LARGE,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val (backgroundColor, contentColor, borderColor) = when (variant) {
        ButtonVariant.PRIMARY -> Triple(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            Color.Transparent,
        )
        ButtonVariant.SECONDARY -> Triple(
            Color.Transparent,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primary,
        )
        ButtonVariant.TERTIARY -> Triple(
            Color.Transparent,
            MaterialTheme.colorScheme.primary,
            Color.Transparent,
        )
        ButtonVariant.DANGER -> Triple(
            MaterialTheme.colorScheme.error,
            MaterialTheme.colorScheme.onError,
            Color.Transparent,
        )
        ButtonVariant.SUCCESS -> Triple(
            Color(0xFF4CAF50),
            Color.White,
            Color.Transparent,
        )
    }

    val paddingHorizontal = when (size) {
        ButtonSize.SMALL -> Spacing.md
        ButtonSize.MEDIUM -> Spacing.lg
        ButtonSize.LARGE -> Spacing.xl
    }

    val textStyle = when (size) {
        ButtonSize.SMALL -> MaterialTheme.typography.labelMedium
        ButtonSize.MEDIUM -> MaterialTheme.typography.labelLarge
        ButtonSize.LARGE -> MaterialTheme.typography.labelLarge
    }

    val interactionSource = remember { MutableInteractionSource() }
    val view = LocalView.current

    val iconSize = when (size) {
        ButtonSize.SMALL -> 16.dp
        ButtonSize.MEDIUM -> 20.dp
        ButtonSize.LARGE -> 24.dp
    }

    Button(
        onClick = {
            view.performClickHaptic()
            onClick()
        },
        enabled = enabled && !isLoading,
        modifier = modifier
            .height(
                when (size) {
                    ButtonSize.SMALL -> ComponentSize.buttonHeightSmall
                    ButtonSize.MEDIUM -> ComponentSize.buttonHeightMedium
                    ButtonSize.LARGE -> ComponentSize.buttonHeightLarge
                },
            ),
        shape = ShapeTokens.button,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        border = when (variant) {
            ButtonVariant.SECONDARY -> BorderStroke(1.5.dp, borderColor)
            else -> null
        },
        contentPadding = PaddingValues(
            horizontal = paddingHorizontal,
            vertical = Spacing.sm,
        ),
        elevation = null,
        interactionSource = interactionSource,
    ) {
        if (isLoading) {
            AppButtonLoadingIndicator(size = size, color = contentColor)
        } else {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (leadingIcon != null) {
                    Box(
                        modifier = Modifier
                            .size(iconSize)
                            .padding(end = if (text != null) Spacing.sm else Spacing.none),
                        contentAlignment = Alignment.Center,
                    ) {
                        leadingIcon()
                    }
                }

                if (!text.isNullOrEmpty()) {
                    Text(
                        text = text,
                        style = textStyle,
                        color = contentColor,
                    )
                }

                if (trailingIcon != null) {
                    Box(
                        modifier = Modifier
                            .size(iconSize)
                            .padding(start = if (text != null) Spacing.sm else Spacing.none),
                        contentAlignment = Alignment.Center,
                    ) {
                        trailingIcon()
                    }
                }
            }
        }
    }
}

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: UiText? = null,
    variant: ButtonVariant = ButtonVariant.PRIMARY,
    size: ButtonSize = ButtonSize.MEDIUM,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val resolvedText = text?.asString()
    AppButton(
        text = resolvedText,
        onClick = onClick,
        modifier = modifier,
        variant = variant,
        size = size,
        enabled = enabled,
        isLoading = isLoading,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
    )
}

@Composable
fun AppButtonLoadingIndicator(
    size: ButtonSize = ButtonSize.MEDIUM,
    color: Color = MaterialTheme.colorScheme.onPrimary,
) {
    val indicatorSize = when (size) {
        ButtonSize.SMALL -> 16.dp
        ButtonSize.MEDIUM -> 20.dp
        ButtonSize.LARGE -> 24.dp
    }

    CircularProgressIndicator(
        modifier = Modifier
            .padding(Spacing.sm)
            .size(indicatorSize),
        color = color,
        strokeWidth = 2.dp,
    )
}
