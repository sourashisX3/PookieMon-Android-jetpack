package com.funapp.pookiemon.core.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ScrollToTopButton(
    gridState: LazyGridState,
    modifier: Modifier = Modifier,
    threshold: Int = 3,
) {
    val showButton by remember {
        derivedStateOf { gridState.firstVisibleItemIndex > threshold }
    }

    val coroutineScope = rememberCoroutineScope()

    ScrollToTopButtonSurface(
        showButton = showButton,
        modifier = modifier,
        onScrollToTop = { coroutineScope.launch { gridState.animateScrollToItem(0) } },
    )
}

@Composable
fun ScrollToTopButton(
    state: LazyListState,
    modifier: Modifier = Modifier,
    threshold: Int = 3,
) {
    val showButton by remember {
        derivedStateOf { state.firstVisibleItemIndex > threshold }
    }

    val coroutineScope = rememberCoroutineScope()

    ScrollToTopButtonSurface(
        showButton = showButton,
        modifier = modifier,
        onScrollToTop = { coroutineScope.launch { state.animateScrollToItem(0) } },
    )
}

@Composable
private fun ScrollToTopButtonSurface(
    showButton: Boolean,
    modifier: Modifier,
    onScrollToTop: () -> Unit,
) {
    val alpha by animateFloatAsState(
        targetValue = if (showButton) 1f else 0f,
        animationSpec = tween(300),
        label = "scrollAlpha",
    )

    if (alpha > 0f) {
        Surface(
            onClick = onScrollToTop,
            modifier = modifier
                .size(44.dp)
                .alpha(alpha),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
            shadowElevation = 6.dp,
            tonalElevation = 3.dp,
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = stringResource(com.funapp.pookiemon.R.string.scroll_to_top),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(22.dp),
                )
            }
        }
    }
}
