package de.agb.blutspende_app.viewmodel

import androidx.lifecycle.ViewModel
import de.agb.blutspende_app.model.ScreenDefinition

class MainActivityViewModel : ViewModel() {

    private val _settingsRoute = ScreenDefinition.Settings.route
    private val _navigationItems = listOf(
        ScreenDefinition.Dashboard,
        ScreenDefinition.Ausweis,
        ScreenDefinition.Blutwerte,
        ScreenDefinition.Vorrat
    )

    // Getter
    val getSettingsRoute: String get() = _settingsRoute
    val getNavigationItems: List<ScreenDefinition> get() = _navigationItems


    fun topAppBarTitle(currentRoute: String?): String {
        return if (currentRoute == "SettingsBlutgruppe") {
            "Settings"
        } else {
            currentRoute ?: ""
        }
    }

}