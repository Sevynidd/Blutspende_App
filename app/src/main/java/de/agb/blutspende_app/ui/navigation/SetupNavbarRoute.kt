package de.agb.blutspende_app.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.agb.blutspende_app.model.ScreenDefinition
import de.agb.blutspende_app.model.roomDatabase.BloodValuesEvent
import de.agb.blutspende_app.model.roomDatabase.BloodValuesState
import de.agb.blutspende_app.ui.screens.BloodValues
import de.agb.blutspende_app.ui.screens.Home
import de.agb.blutspende_app.ui.screens.Settings
import de.agb.blutspende_app.ui.screens.SettingsBloodgroup
import de.agb.blutspende_app.ui.screens.SettingsBloodgroupWebview
import de.agb.blutspende_app.ui.screens.Supply

@Composable
fun SetupNavbarGraph(
    navController: NavHostController,
    databaseState: BloodValuesState,
    databaseOnEvent: (BloodValuesEvent) -> Unit
) {
    NavHost(navController = navController, startDestination = ScreenDefinition.Dashboard.route,
        enterTransition = {
            fadeIn(
                animationSpec = tween(
                    200, easing = LinearEasing
                )
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(
                    200, easing = LinearEasing
                )
            )
        }) {
        composable(route = ScreenDefinition.Dashboard.route) {
            Home()
        }
        composable(route = ScreenDefinition.BloodValues.route) {
            BloodValues(databaseState, databaseOnEvent)
        }
        composable(route = ScreenDefinition.Supply.route) {
            Supply()
        }
        composable(route = ScreenDefinition.Settings.route) {
            Settings(navController)
        }
        composable(route = ScreenDefinition.SettingsBloodgroup.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        200, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(200, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        200, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(200, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }) {
            SettingsBloodgroup(navController)
        }
        composable(route = ScreenDefinition.SettingsBloodgroupWebview.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        200, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(200, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        200, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(200, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.Right
                )
            }) {
            SettingsBloodgroupWebview()
        }
    }
}