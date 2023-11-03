package de.agb.blutspende_app.model

import de.agb.blutspende_app.R

sealed class ScreenDefinition(val route: String, val iconId: Int) {
    data object Dashboard : ScreenDefinition(route = "Dashboard", R.drawable.dashboard)
    data object BloodValues : ScreenDefinition(route = "Blutwerte", R.drawable.blood_drop)
    data object Supply : ScreenDefinition(route = "Vorrat", R.drawable.blood_bag_default)
    data object Settings :
        ScreenDefinition(route = "Settings", R.drawable.settings)

    data object SettingsBloodgroup :
        ScreenDefinition(route = "SettingsBlutgruppe", R.drawable.blood_drop)

    data object SettingsBloodgroupWebview :
        ScreenDefinition(route = "SettingsBlutgruppe_Webview", R.drawable.blood_drop)
}
