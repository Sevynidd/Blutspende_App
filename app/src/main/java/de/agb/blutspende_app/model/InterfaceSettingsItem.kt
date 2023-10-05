package de.agb.blutspende_app.model

interface InterfaceSettingsItem {
    val title: String
    val subTitle: String?
        get() = null

    val icon: Int
    val onClick: () -> Unit
    val rightArrowButtonVisible: Boolean
        get() = false
}