package agb.loehne.blutspende_app.models

import agb.loehne.blutspende_app.R

sealed class ScreenDefinition(val route: String, val name: String, val iconId: Int) {
    object Dashboard : ScreenDefinition(route = "Dashboard", "Dashboard", R.drawable.dashboard)
    object Ausweis : ScreenDefinition(route = "Ausweis", "Ausweis", R.drawable.id_badge)
    object Blutwerte : ScreenDefinition(route = "Blutwerte", "Werte", R.drawable.blood_drop)
    object Vorrat : ScreenDefinition(route = "Vorrat", "Vorrat", R.drawable.blood_bag_default)
    object Settings :
        ScreenDefinition(route = "Settings", "Settings", R.drawable.settings)

    object SettingsBlutgruppe :
        ScreenDefinition(route = "SettingsBlutgruppe", "SettingsBlutgruppe", R.drawable.blood_drop)
}
