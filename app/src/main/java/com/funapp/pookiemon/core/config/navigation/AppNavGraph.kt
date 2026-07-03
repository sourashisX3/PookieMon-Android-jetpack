package com.funapp.pookiemon.core.config.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.funapp.pookiemon.R
import com.funapp.pookiemon.core.ui.components.navigation.FloatingNavBar
import com.funapp.pookiemon.feature.berry.presentation.screens.BerryDetailScreen
import com.funapp.pookiemon.feature.berry.presentation.screens.BerryListScreen
import com.funapp.pookiemon.feature.encounter.presentation.screens.EncounterDetailScreen
import com.funapp.pookiemon.feature.encounter.presentation.screens.EncounterListScreen
import com.funapp.pookiemon.feature.evolution.presentation.screens.EvolutionViewerScreen
import com.funapp.pookiemon.feature.explore.presentation.screens.ExploreScreen
import com.funapp.pookiemon.feature.games.presentation.screens.GamesListScreen
import com.funapp.pookiemon.feature.games.presentation.screens.GenerationDetailScreen
import com.funapp.pookiemon.feature.item.presentation.screens.ItemDetailScreen
import com.funapp.pookiemon.feature.item.presentation.screens.ItemListScreen
import com.funapp.pookiemon.feature.location.presentation.screens.LocationDetailScreen
import com.funapp.pookiemon.feature.location.presentation.screens.LocationListScreen
import com.funapp.pookiemon.feature.move.presentation.screens.MoveDetailScreen
import com.funapp.pookiemon.feature.move.presentation.screens.MoveListScreen
import com.funapp.pookiemon.feature.pokemon.presentation.screens.detail.DetailScreen
import com.funapp.pookiemon.feature.pokemon.presentation.screens.list.PokemonListScreen
import com.funapp.pookiemon.feature.references.presentation.screens.AbilityDetailScreen
import com.funapp.pookiemon.feature.references.presentation.screens.NatureDetailScreen
import com.funapp.pookiemon.feature.references.presentation.screens.ReferencesScreen
import com.funapp.pookiemon.feature.references.presentation.screens.TypeDetailScreen
import com.funapp.pookiemon.feature.settings.presentation.screens.SettingsScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = currentDestination?.hasRoute<Route.PokemonListRoute>() == true ||
        currentDestination?.hasRoute<Route.ItemListRoute>() == true ||
        currentDestination?.hasRoute<Route.MoveListRoute>() == true ||
        currentDestination?.hasRoute<Route.ExploreRoute>() == true ||
        currentDestination?.hasRoute<Route.BerryListRoute>() == true ||
        currentDestination?.hasRoute<Route.EvolutionViewerRoute>() == true ||
        currentDestination?.hasRoute<Route.LocationListRoute>() == true ||
        currentDestination?.hasRoute<Route.EncounterListRoute>() == true ||
        currentDestination?.hasRoute<Route.GamesListRoute>() == true ||
        currentDestination?.hasRoute<Route.ReferencesRoute>() == true ||
        currentDestination?.hasRoute<Route.SettingsRoute>() == true

    Scaffold { innerPadding ->
            Box(Modifier.fillMaxSize()) {
                SharedTransitionLayout {
                    NavHost(
                    navController = navController,
                    startDestination = Route.PookieMonRoute,
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                navigation<Route.PookieMonRoute>(
                    startDestination = Route.PokemonListRoute,
                ) {
                    composable<Route.PokemonListRoute> {
                        val sharedTransitionScope: SharedTransitionScope = this@SharedTransitionLayout
                        val animatedVisibilityScope: AnimatedVisibilityScope = this
                        PokemonListScreen(
                            navController = navController,
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                    }

                    composable<Route.PokemonDetailRoute>(
                        enterTransition = {
                            fadeIn(animationSpec = tween(300, delayMillis = 80)) +
                                scaleIn(initialScale = 0.92f, animationSpec = tween(350))
                        },
                        exitTransition = {
                            fadeOut(animationSpec = tween(200))
                        },
                        popEnterTransition = {
                            fadeIn(animationSpec = tween(200))
                        },
                        popExitTransition = {
                            fadeOut(animationSpec = tween(200)) +
                                scaleOut(targetScale = 0.92f, animationSpec = tween(250))
                        },
                    ) { backStackEntry ->
                        val sharedTransitionScope: SharedTransitionScope = this@SharedTransitionLayout
                        val animatedVisibilityScope: AnimatedVisibilityScope = this
                        val route = backStackEntry.toRoute<Route.PokemonDetailRoute>()
                        DetailScreen(
                            onNavigateBack = { navController.popBackStack() },
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                    }
                }

                composable<Route.ItemListRoute> {
                    val sharedTransitionScope: SharedTransitionScope = this@SharedTransitionLayout
                    val animatedVisibilityScope: AnimatedVisibilityScope = this
                    ItemListScreen(
                        navController = navController,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                }

                composable<Route.ItemDetailRoute>(
                    enterTransition = {
                        fadeIn(animationSpec = tween(300, delayMillis = 80)) +
                            scaleIn(initialScale = 0.92f, animationSpec = tween(350))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(200))
                    },
                    popEnterTransition = {
                        fadeIn(animationSpec = tween(200))
                    },
                    popExitTransition = {
                        fadeOut(animationSpec = tween(200)) +
                            scaleOut(targetScale = 0.92f, animationSpec = tween(250))
                    },
                ) {
                    val sharedTransitionScope: SharedTransitionScope = this@SharedTransitionLayout
                    val animatedVisibilityScope: AnimatedVisibilityScope = this
                    ItemDetailScreen(
                        onNavigateBack = { navController.popBackStack() },
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                }

                composable<Route.MoveListRoute> {
                    val sharedTransitionScope: SharedTransitionScope = this@SharedTransitionLayout
                    val animatedVisibilityScope: AnimatedVisibilityScope = this
                    MoveListScreen(
                        navController = navController,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                }

                composable<Route.MoveDetailRoute>(
                    enterTransition = {
                        fadeIn(animationSpec = tween(300, delayMillis = 80)) +
                            scaleIn(initialScale = 0.92f, animationSpec = tween(350))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(200))
                    },
                    popEnterTransition = {
                        fadeIn(animationSpec = tween(200))
                    },
                    popExitTransition = {
                        fadeOut(animationSpec = tween(200)) +
                            scaleOut(targetScale = 0.92f, animationSpec = tween(250))
                    },
                ) {
                    val sharedTransitionScope: SharedTransitionScope = this@SharedTransitionLayout
                    val animatedVisibilityScope: AnimatedVisibilityScope = this
                    MoveDetailScreen(
                        onNavigateBack = { navController.popBackStack() },
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                }

                composable<Route.ExploreRoute> {
                    ExploreScreen(
                        onCategoryClick = { category ->
                            val route: Route = when (category) {
                                "berries" -> Route.BerryListRoute()
                                "locations" -> Route.LocationListRoute()
                                "evolution" -> Route.EvolutionViewerRoute()
                                "encounters" -> Route.EncounterListRoute()
                                "games" -> Route.GamesListRoute
                                "references" -> Route.ReferencesRoute
                                else -> Route.BerryListRoute()
                            }
                            navController.navigate(route)
                        },
                    )
                }

                composable<Route.BerryListRoute> {
                    BerryListScreen(navController = navController)
                }

                composable<Route.BerryDetailRoute>(
                    enterTransition = {
                        fadeIn(animationSpec = tween(300, delayMillis = 80)) +
                            scaleIn(initialScale = 0.92f, animationSpec = tween(350))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(200))
                    },
                    popEnterTransition = {
                        fadeIn(animationSpec = tween(200))
                    },
                    popExitTransition = {
                        fadeOut(animationSpec = tween(200)) +
                            scaleOut(targetScale = 0.92f, animationSpec = tween(250))
                    },
                ) {
                    BerryDetailScreen(
                        onNavigateBack = { navController.popBackStack() },
                    )
                }

                composable<Route.EvolutionViewerRoute> {
                    EvolutionViewerScreen(
                        onNavigateBack = { navController.popBackStack() },
                    )
                }

                composable<Route.LocationListRoute> {
                    LocationListScreen(navController = navController)
                }

                composable<Route.LocationDetailRoute>(
                    enterTransition = {
                        fadeIn(animationSpec = tween(300, delayMillis = 80)) +
                            scaleIn(initialScale = 0.92f, animationSpec = tween(350))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(200))
                    },
                    popEnterTransition = {
                        fadeIn(animationSpec = tween(200))
                    },
                    popExitTransition = {
                        fadeOut(animationSpec = tween(200)) +
                            scaleOut(targetScale = 0.92f, animationSpec = tween(250))
                    },
                ) {
                    LocationDetailScreen(
                        onNavigateBack = { navController.popBackStack() },
                    )
                }

                composable<Route.EncounterListRoute> {
                    EncounterListScreen(navController = navController)
                }

                composable<Route.EncounterDetailRoute>(
                    enterTransition = {
                        fadeIn(animationSpec = tween(300, delayMillis = 80)) +
                            scaleIn(initialScale = 0.92f, animationSpec = tween(350))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(200))
                    },
                    popEnterTransition = {
                        fadeIn(animationSpec = tween(200))
                    },
                    popExitTransition = {
                        fadeOut(animationSpec = tween(200)) +
                            scaleOut(targetScale = 0.92f, animationSpec = tween(250))
                    },
                ) {
                    EncounterDetailScreen(
                        onNavigateBack = { navController.popBackStack() },
                    )
                }

                composable<Route.GamesListRoute> {
                    GamesListScreen(
                        onNavigateBack = { navController.popBackStack() },
                        onGenerationClick = { genId -> navController.navigate(Route.GenerationDetailRoute(genId)) },
                    )
                }

                composable<Route.GenerationDetailRoute>(
                    enterTransition = {
                        fadeIn(animationSpec = tween(300, delayMillis = 80)) +
                            scaleIn(initialScale = 0.92f, animationSpec = tween(350))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(200))
                    },
                    popEnterTransition = {
                        fadeIn(animationSpec = tween(200))
                    },
                    popExitTransition = {
                        fadeOut(animationSpec = tween(200)) +
                            scaleOut(targetScale = 0.92f, animationSpec = tween(250))
                    },
                ) {
                    GenerationDetailScreen(
                        onNavigateBack = { navController.popBackStack() },
                    )
                }

                composable<Route.ReferencesRoute> {
                    ReferencesScreen(
                        onNavigateBack = { navController.popBackStack() },
                        onAbilityClick = { id -> navController.navigate(Route.AbilityDetailRoute(id)) },
                        onTypeClick = { id -> navController.navigate(Route.TypeDetailRoute(id)) },
                        onNatureClick = { id -> navController.navigate(Route.NatureDetailRoute(id)) },
                    )
                }

                composable<Route.AbilityDetailRoute>(
                    enterTransition = {
                        fadeIn(animationSpec = tween(300, delayMillis = 80)) +
                            scaleIn(initialScale = 0.92f, animationSpec = tween(350))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(200))
                    },
                    popEnterTransition = {
                        fadeIn(animationSpec = tween(200))
                    },
                    popExitTransition = {
                        fadeOut(animationSpec = tween(200)) +
                            scaleOut(targetScale = 0.92f, animationSpec = tween(250))
                    },
                ) {
                    AbilityDetailScreen(
                        onNavigateBack = { navController.popBackStack() },
                    )
                }

                composable<Route.TypeDetailRoute>(
                    enterTransition = {
                        fadeIn(animationSpec = tween(300, delayMillis = 80)) +
                            scaleIn(initialScale = 0.92f, animationSpec = tween(350))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(200))
                    },
                    popEnterTransition = {
                        fadeIn(animationSpec = tween(200))
                    },
                    popExitTransition = {
                        fadeOut(animationSpec = tween(200)) +
                            scaleOut(targetScale = 0.92f, animationSpec = tween(250))
                    },
                ) {
                    TypeDetailScreen(
                        onNavigateBack = { navController.popBackStack() },
                    )
                }

                composable<Route.NatureDetailRoute>(
                    enterTransition = {
                        fadeIn(animationSpec = tween(300, delayMillis = 80)) +
                            scaleIn(initialScale = 0.92f, animationSpec = tween(350))
                    },
                    exitTransition = {
                        fadeOut(animationSpec = tween(200))
                    },
                    popEnterTransition = {
                        fadeIn(animationSpec = tween(200))
                    },
                    popExitTransition = {
                        fadeOut(animationSpec = tween(200)) +
                            scaleOut(targetScale = 0.92f, animationSpec = tween(250))
                    },
                ) {
                    NatureDetailScreen(
                        onNavigateBack = { navController.popBackStack() },
                    )
                }

                composable<Route.SettingsRoute> {
                    SettingsScreen(
                        onNavigateBack = { navController.popBackStack() },
                    )
                }
            }
            }

                if (showBottomBar) {
                    FloatingNavBar(
                        currentDestination = currentDestination,
                        onPokemonClick = {
                            navController.navigate(Route.PokemonListRoute) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        onTabClick = { route ->
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        modifier = Modifier.align(Alignment.BottomCenter),
                    )
                }
            }
        }
}
 
@Composable
private fun ComingSoonPlaceholder(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "$title - ${stringResource(R.string.coming_soon)}",
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}
