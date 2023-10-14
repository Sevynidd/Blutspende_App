package de.agb.blutspende_app.viewmodel.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class VMSettingsBlutgruppe : ViewModel() {
    private var _showDialog = mutableStateOf(false)

    val getShowDialog: Boolean
        get() = _showDialog.value

    fun setShowDialog(value: Boolean) {
        _showDialog.value = value
    }

}