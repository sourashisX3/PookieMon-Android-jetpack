package com.funapp.pookiemon.core.ui.components.checkbox

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import com.funapp.pookiemon.core.utils.performClickHaptic

@Composable
fun AppCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Checkbox(
        checked = checked,
        onCheckedChange = if (onCheckedChange != null) {
            val view = LocalView.current
            {
                view.performClickHaptic()
                onCheckedChange(it)
            }
        } else null,
        modifier = modifier,
        enabled = enabled,
        colors = CheckboxDefaults.colors(
            checkedColor = MaterialTheme.colorScheme.primary,
            uncheckedColor = MaterialTheme.colorScheme.outline,
            checkmarkColor = MaterialTheme.colorScheme.onPrimary,
            disabledCheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            disabledUncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            disabledIndeterminateColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        ),
        interactionSource = remember { MutableInteractionSource() },
    )
}

@Composable
fun AppTriStateCheckbox(
    state: Boolean?,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val isChecked = state == true

    Checkbox(
        checked = isChecked,
        onCheckedChange = {
            // Haptic handled in AppCheckbox's onCheckedChange wrapper
            onClick?.invoke()
        },
        modifier = modifier,
        enabled = enabled,
        colors = CheckboxDefaults.colors(
            checkedColor = MaterialTheme.colorScheme.primary,
            uncheckedColor = MaterialTheme.colorScheme.outline,
            checkmarkColor = MaterialTheme.colorScheme.onPrimary,
            disabledCheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            disabledUncheckedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            disabledIndeterminateColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        ),
        interactionSource = remember { MutableInteractionSource() },
    )
}
