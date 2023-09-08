package agb.loehne.blutspende_app

sealed class ScreenDefinition(val route: String, val name: String, val iconId: Int) {
    object Home : ScreenDefinition(route = "Home", "Home", R.drawable.home)
    object Ausweis : ScreenDefinition(route = "Ausweis", "Ausweis", R.drawable.id_badge)
    object Blutwerte : ScreenDefinition(route = "Blutwerte", "Blutwerte", R.drawable.blood_drop)
    object Vorrat : ScreenDefinition(route = "Vorrat", "Vorrat", R.drawable.blood_bag_default)
    object Einstellungen :
        ScreenDefinition(route = "Einstellungen", "Einstellungen", R.drawable.settings)
}
