package com.funapp.pookiemon.core.config.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.funapp.pookiemon.feature.pokemon.presentation.screens.detail.DetailScreen
import com.funapp.pookiemon.feature.pokemon.presentation.screens.list.PokemonListScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = Route.PookieMonRoute,
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
        }
    }
}
