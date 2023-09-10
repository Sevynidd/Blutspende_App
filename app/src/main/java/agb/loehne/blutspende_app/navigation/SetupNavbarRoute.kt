package agb.loehne.blutspende_app.navigation

import agb.loehne.blutspende_app.screens.Ausweis
import agb.loehne.blutspende_app.screens.Blutwerte
import agb.loehne.blutspende_app.screens.Einstellungen
import agb.loehne.blutspende_app.screens.Home
import agb.loehne.blutspende_app.screens.Vorrat
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = ScreenDefinition.Home.route) {
        composable(route = ScreenDefinition.Home.route) {
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
        composable(route = ScreenDefinition.Einstellungen.route) {
            Einstellungen()
        }
    }
}