package de.agb.blutspende_app.viewmodel.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BlutgruppeViewModel : ViewModel() {
    private var _showDialogBlutgruppe = mutableStateOf(false)

    // Getter
    val getShowDialogBlutgruppe: Boolean get() = _showDialogBlutgruppe.value

    // Setter
    fun setShowDialogBlutgruppe(value: Boolean) {
        _showDialogBlutgruppe.value = value
    }

}