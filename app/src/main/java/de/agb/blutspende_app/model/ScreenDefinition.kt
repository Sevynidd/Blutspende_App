package de.agb.blutspende_app.model

import de.agb.blutspende_app.R

sealed class ScreenDefinition(val route: String, val iconId: Int) {
    data object Dashboard : ScreenDefinition(route = "Dashboard", R.drawable.dashboard)
    data object Blutwerte : ScreenDefinition(route = "Blutwerte", R.drawable.blood_drop)
    data object Vorrat : ScreenDefinition(route = "Vorrat", R.drawable.blood_bag_default)
    data object Settings :
        ScreenDefinition(route = "Settings", R.drawable.settings)

    data object SettingsBlutgruppe :
        ScreenDefinition(route = "SettingsBlutgruppe", R.drawable.blood_drop)
}
