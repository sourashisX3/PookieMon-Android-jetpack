package com.funapp.pookiemon.core.ui.components.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.funapp.pookiemon.R
import com.funapp.pookiemon.core.config.navigation.Route
import kotlin.reflect.KClass
import kotlin.math.roundToInt

private data class NavTab(
    val labelRes: Int,
    val icon: ImageVector,
    val routeClass: KClass<out Route>,
    val route: Route,
)

@Composable
fun FloatingNavBar(
    currentDestination: NavDestination?,
    onPokemonClick: () -> Unit,
    onTabClick: (Route) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs = listOf(
        NavTab(R.string.tab_pokemon, Icons.Default.CatchingPokemon, Route.PokemonListRoute::class, Route.PokemonListRoute),
        NavTab(R.string.tab_items, Icons.Default.HistoryEdu, Route.ItemListRoute::class, Route.ItemListRoute),
        NavTab(R.string.tab_moves, Icons.Default.Star, Route.MoveListRoute::class, Route.MoveListRoute),
        NavTab(R.string.tab_explore, Icons.Default.Explore, Route.ExploreRoute::class, Route.ExploreRoute),
        NavTab(R.string.tab_settings, Icons.Default.Settings, Route.SettingsRoute::class, Route.SettingsRoute),
    )

    val density = LocalDensity.current
    var rowRootOffset by remember { mutableStateOf(0f) }
    var selectedTabOffset by remember { mutableStateOf(0f) }
    var selectedTabWidth by remember { mutableStateOf(0f) }

    val animatedOffset by animateDpAsState(
        targetValue = with(density) { selectedTabOffset.toDp() },
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing,
        ),
        label = "indicatorOffset",
    )
    val animatedWidth by animateDpAsState(
        targetValue = with(density) { selectedTabWidth.toDp() },
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing,
        ),
        label = "indicatorWidth",
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(bottom = 24.dp),
        shape = RoundedCornerShape(24.dp),
        shadowElevation = 12.dp,
        tonalElevation = 6.dp,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(vertical = 10.dp),
        ) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(animatedOffset.roundToPx(), 0) }
                    .width(animatedWidth)
                    .fillMaxHeight()
                    .align(Alignment.CenterStart)
                    .clip(RoundedCornerShape(18.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        rowRootOffset = coordinates.boundsInRoot().left
                    },
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                tabs.forEach { tab ->
                    val isSelected = currentDestination?.hasRoute(tab.routeClass) == true

                    Box(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                if (isSelected) {
                                    selectedTabOffset = coordinates.boundsInRoot().left - rowRootOffset
                                    selectedTabWidth = coordinates.size.width.toFloat()
                                }
                            }
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    if (tab.route is Route.PokemonListRoute) onPokemonClick()
                                    else onTabClick(tab.route)
                                },
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            AnimatedContent(
                                targetState = isSelected,
                                transitionSpec = {
                                    fadeIn(animationSpec = tween(250)) togetherWith
                                        fadeOut(animationSpec = tween(250))
                                },
                                label = "iconAnim",
                            ) { selected ->
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = stringResource(tab.labelRes),
                                    tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(20.dp),
                                )
                            }

                            if (isSelected) {
                                Text(
                                    text = stringResource(tab.labelRes),
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    modifier = Modifier.padding(start = 4.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
