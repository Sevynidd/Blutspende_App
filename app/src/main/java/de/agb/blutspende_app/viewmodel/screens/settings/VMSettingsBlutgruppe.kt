package de.agb.blutspende_app.viewmodel.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class VMSettingsBlutgruppe : ViewModel() {
    private var _isVisibleAB0System = mutableStateOf(false)

    val getIsVisibleAB0System: Boolean
        get() = _isVisibleAB0System.value

    fun setIsVisibleAB0System(value: Boolean) {
        _isVisibleAB0System.value = value
    }

}