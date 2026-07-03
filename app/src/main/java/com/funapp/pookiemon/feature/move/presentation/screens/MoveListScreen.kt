package com.funapp.pookiemon.feature.move.presentation.screens

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.funapp.pookiemon.R
import com.funapp.pookiemon.core.config.navigation.Route
import com.funapp.pookiemon.core.ui.components.AppErrorView
import com.funapp.pookiemon.core.ui.components.AppTopBar
import com.funapp.pookiemon.core.ui.components.ScrollToTopButton
import com.funapp.pookiemon.core.ui.components.rememberShimmerBrush
import com.funapp.pookiemon.core.ui.components.shimmerEffect
import com.funapp.pookiemon.feature.move.presentation.components.MoveCard
import com.funapp.pookiemon.feature.move.presentation.events.MoveListUiEvent
import com.funapp.pookiemon.feature.move.presentation.viewmodels.MoveListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoveListScreen(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
    viewModel: MoveListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val layoutInfo = gridState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisible >= totalItems - 4 && totalItems > 0 && uiState.hasMore && !uiState.isLoadingMore
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            viewModel.onEvent(MoveListUiEvent.LoadMoreMoves)
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.tab_moves),
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            PullToRefreshBox(
                isRefreshing = uiState.isLoading,
                onRefresh = { viewModel.onEvent(MoveListUiEvent.LoadMoves) },
                modifier = Modifier.fillMaxSize(),
            ) {
                when {
                    uiState.isLoading -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            items(6) {
                                MoveCardShimmer()
                            }
                        }
                    }

                    uiState.error != null -> {
                        AppErrorView(
                            message = uiState.error ?: stringResource(R.string.unknown_error),
                            onRetry = { viewModel.onEvent(MoveListUiEvent.RetryClicked) },
                            modifier = Modifier.fillMaxSize(),
                        )
                    }

                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            state = gridState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            items(
                                items = uiState.moves,
                                key = { it.id },
                            ) { move ->
                                MoveCard(
                                    move = move,
                                    onClick = {
                                        navController.navigate(Route.MoveDetailRoute(move.id))
                                    },
                                    modifier = Modifier.animateItem(),
                                    sharedTransitionScope = sharedTransitionScope,
                                    animatedVisibilityScope = animatedVisibilityScope,
                                )
                            }

                            if (uiState.isLoadingMore) {
                                items(2) {
                                    MoveCardShimmer()
                                }
                            }

                            item {
                                Box(modifier = Modifier.height(96.dp))
                            }
                        }
                    }
                }
            }

            ScrollToTopButton(
                gridState = gridState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 96.dp),
            )
        }
    }
}

@Composable
fun MoveCardShimmer(modifier: Modifier = Modifier) {
    val brush = rememberShimmerBrush()

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shimmerEffect(brush, RoundedCornerShape(12.dp)),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(16.dp)
                    .shimmerEffect(brush, RoundedCornerShape(4.dp)),
            )

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .width(36.dp)
                        .height(14.dp)
                        .shimmerEffect(brush, RoundedCornerShape(4.dp)),
                )
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(12.dp)
                        .shimmerEffect(brush, RoundedCornerShape(4.dp)),
                )
                Box(
                    modifier = Modifier
                        .width(36.dp)
                        .height(12.dp)
                        .shimmerEffect(brush, RoundedCornerShape(4.dp)),
                )
            }
        }
    }
}
