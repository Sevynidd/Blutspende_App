package de.agb.blutspende_app.model

interface ISettingsItem {
    val title: String
    val subTitle: String?
        get() = null

    val icon: Int
    val onClick: () -> Unit
    val rightArrowButtonVisible: Boolean
        get() = false
}