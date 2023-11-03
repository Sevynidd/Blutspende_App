package de.agb.blutspende_app.viewmodel

import androidx.lifecycle.ViewModel
import de.agb.blutspende_app.model.ScreenDefinition

class VMMainActivity : ViewModel() {

    private val _navigationItems = listOf(
        ScreenDefinition.Dashboard,
        ScreenDefinition.BloodValues,
        ScreenDefinition.Supply
    )

    val getNavigationItems: List<ScreenDefinition> get() = _navigationItems
}