package de.agb.blutspende_app.viewmodel.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SettingsBlutgruppeViewModel : ViewModel() {
    private var _showDialog = mutableStateOf(false)

    val getShowDialog: Boolean
        get() = _showDialog.value

    fun setShowDialog(value: Boolean) {
        _showDialog.value = value
    }

}