package agb.loehne.blutspende_app.view

import agb.loehne.blutspende_app.model.ScreenDefinition
import agb.loehne.blutspende_app.view.screens.Ausweis
import agb.loehne.blutspende_app.view.screens.Blutwerte
import agb.loehne.blutspende_app.view.screens.Settings
import agb.loehne.blutspende_app.view.screens.Home
import agb.loehne.blutspende_app.view.screens.Vorrat
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavbarGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = ScreenDefinition.Dashboard.route) {
        composable(route = ScreenDefinition.Dashboard.route) {
            Home()
        }
        composable(route = ScreenDefinition.Ausweis.route) {
            Ausweis()
        }
        composable(route = ScreenDefinition.Blutwerte.route) {
            Blutwerte()
        }
        composable(route = ScreenDefinition.Vorrat.route) {
            Vorrat()
        }
        composable(route = ScreenDefinition.Settings.route) {
            Settings()
        }
    }
}