package agb.loehne.blutspende_app.ui.navigation

import agb.loehne.blutspende_app.models.ScreenDefinition
import agb.loehne.blutspende_app.ui.screens.Ausweis
import agb.loehne.blutspende_app.ui.screens.Blutwerte
import agb.loehne.blutspende_app.ui.screens.Settings
import agb.loehne.blutspende_app.ui.screens.Home
import agb.loehne.blutspende_app.ui.screens.SettingsBlutgruppe
import agb.loehne.blutspende_app.ui.screens.Vorrat
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
            Settings(navController)
        }
        composable(route = ScreenDefinition.SettingsBlutgruppe.route) {
            SettingsBlutgruppe()
        }
    }
}