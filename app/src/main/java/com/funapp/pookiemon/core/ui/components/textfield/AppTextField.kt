package com.funapp.pookiemon.core.ui.components.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.funapp.pookiemon.core.theme.ComponentSize
import com.funapp.pookiemon.core.theme.ShapeTokens
import com.funapp.pookiemon.core.theme.Spacing

enum class TextFieldVariant {
    OUTLINED,
    FILLED,
}

enum class TextFieldHeight {
    COMPACT,
    REGULAR,
    TALL,
}

typealias InputValidator = (String) -> String?

@Composable
fun IconWrapper(
    icon: @Composable () -> Unit,
    size: androidx.compose.ui.unit.Dp = ComponentSize.iconSmall,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(LocalContentColor provides color) {
            icon()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    variant: TextFieldVariant = TextFieldVariant.OUTLINED,
    height: TextFieldHeight = TextFieldHeight.REGULAR,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    validator: InputValidator? = null,
    onValidationChange: ((Boolean) -> Unit)? = null,
) {
    val validationError = validator?.invoke(value)
    val hasValidationError = validationError != null
    val finalIsError = isError
    val finalErrorMessage = errorMessage ?: validationError

    LaunchedEffect(hasValidationError) {
        onValidationChange?.invoke(!hasValidationError)
    }

    val shape = ShapeTokens.textField
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value

    val heightDp = when (height) {
        TextFieldHeight.COMPACT -> 40.dp
        TextFieldHeight.REGULAR -> 56.dp
        TextFieldHeight.TALL -> 72.dp
    }

    val borderColor = when {
        finalIsError -> MaterialTheme.colorScheme.error
        isFocused -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.outline
    }

    val iconColor = when {
        !enabled -> MaterialTheme.colorScheme.onSurfaceVariant
        finalIsError -> MaterialTheme.colorScheme.error
        isFocused -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val wrappedLeadingIcon = if (leadingIcon != null) {
        @Composable {
            IconWrapper(
                icon = leadingIcon,
                size = ComponentSize.iconSmall,
                color = iconColor,
            )
        }
    } else null

    val wrappedTrailingIcon = if (trailingIcon != null) {
        @Composable {
            IconWrapper(
                icon = trailingIcon,
                size = ComponentSize.iconSmall,
                color = iconColor,
            )
        }
    } else null

    when (variant) {
        TextFieldVariant.OUTLINED -> {
            Column(modifier = modifier) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(heightDp)
                        .border(width = 1.5.dp, color = borderColor, shape = shape)
                        .background(color = MaterialTheme.colorScheme.surface, shape = shape),
                ) {
                    TextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(heightDp),
                        label = if (label.isNotEmpty()) {
                            { Text(label) }
                        } else null,
                        placeholder = if (placeholder.isNotEmpty()) {
                            { Text(placeholder) }
                        } else null,
                        enabled = enabled,
                        isError = finalIsError,
                        leadingIcon = wrappedLeadingIcon,
                        trailingIcon = wrappedTrailingIcon,
                        visualTransformation = visualTransformation,
                        maxLines = maxLines,
                        singleLine = singleLine,
                        shape = shape,
                        textStyle = MaterialTheme.typography.bodyMedium,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            errorTextColor = MaterialTheme.colorScheme.error,
                            focusedLabelColor = if (finalIsError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            errorLabelColor = MaterialTheme.colorScheme.error,
                            focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                        interactionSource = interactionSource,
                    )
                }

                if (finalIsError && finalErrorMessage != null) {
                    Text(
                        text = finalErrorMessage,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = Spacing.sm, start = Spacing.md),
                    )
                }
            }
        }

        TextFieldVariant.FILLED -> {
            Column(modifier = modifier) {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = if (label.isNotEmpty()) {
                        { Text(label) }
                    } else null,
                    placeholder = if (placeholder.isNotEmpty()) {
                        { Text(placeholder) }
                    } else null,
                    enabled = enabled,
                    isError = finalIsError,
                    leadingIcon = wrappedLeadingIcon,
                    trailingIcon = wrappedTrailingIcon,
                    visualTransformation = visualTransformation,
                    maxLines = maxLines,
                    singleLine = singleLine,
                    shape = shape,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = MaterialTheme.colorScheme.error,
                        disabledIndicatorColor = Color.Transparent,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        errorTextColor = MaterialTheme.colorScheme.error,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        errorLabelColor = MaterialTheme.colorScheme.error,
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                    interactionSource = interactionSource,
                )

                if (finalIsError && finalErrorMessage != null) {
                    Text(
                        text = finalErrorMessage,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(top = Spacing.sm, start = Spacing.md),
                    )
                }
            }
        }
    }
}

@Composable
fun AppPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    placeholder: String = "Enter password",
    variant: TextFieldVariant = TextFieldVariant.OUTLINED,
    height: TextFieldHeight = TextFieldHeight.REGULAR,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    showPassword: Boolean = false,
    validator: InputValidator? = null,
    onValidationChange: ((Boolean) -> Unit)? = null,
) {
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        variant = variant,
        height = height,
        enabled = enabled,
        isError = isError,
        errorMessage = errorMessage,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        validator = validator,
        onValidationChange = onValidationChange,
    )
}

@Composable
fun AppMultiLineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    placeholder: String = "",
    variant: TextFieldVariant = TextFieldVariant.OUTLINED,
    height: TextFieldHeight = TextFieldHeight.TALL,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    maxLines: Int = 5,
    validator: InputValidator? = null,
    onValidationChange: ((Boolean) -> Unit)? = null,
) {
    AppTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        variant = variant,
        height = height,
        enabled = enabled,
        isError = isError,
        errorMessage = errorMessage,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        maxLines = maxLines,
        singleLine = false,
        validator = validator,
        onValidationChange = onValidationChange,
    )
}

object InputValidators {
    fun email(value: String): String? {
        return if (value.isEmpty()) "Email is required"
        else if (!value.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))) "Invalid email format"
        else null
    }

    fun password(value: String): String? {
        return when {
            value.isEmpty() -> "Password is required"
            value.length < 8 -> "Password must be at least 8 characters"
            else -> null
        }
    }

    fun username(value: String): String? {
        return when {
            value.isEmpty() -> "Username is required"
            value.length < 3 -> "Username must be at least 3 characters"
            else -> null
        }
    }

    fun required(value: String): String? {
        return if (value.isBlank()) "This field is required" else null
    }

    fun minLength(minChars: Int): InputValidator = { value ->
        if (value.length < minChars) "Must be at least $minChars characters" else null
    }

    fun maxLength(maxChars: Int): InputValidator = { value ->
        if (value.length > maxChars) "Must not exceed $maxChars characters" else null
    }

    fun combine(vararg validators: InputValidator): InputValidator = { value ->
        validators.firstNotNullOfOrNull { it(value) }
    }
}
