package de.agb.blutspende_app.viewmodel.settings

import de.agb.blutspende_app.R
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import de.agb.blutspende_app.model.ScreenDefinition

class SettingsViewModel : ViewModel() {

    // DARSTELLUNG

    private var _showDialogDarstellung = mutableStateOf(false)
    private val _radioOptionsDarstellung = listOf("System", "Hell", "Dunkel")
    private val _imageIdsDarstellung =
        listOf(R.drawable.ui_system, R.drawable.ui_hell, R.drawable.ui_dunkel)

    val getShowDialogDarstellung: Boolean
        get() = _showDialogDarstellung.value
    val getRadioOptionsDarstellung: List<String>
        get() = _radioOptionsDarstellung
    val getImageIdsDarstellung: List<Int>
        get() = _imageIdsDarstellung

    fun setShowDialogDarstellung(value: Boolean) {
        _showDialogDarstellung.value = value
    }

    // BLUTGRUPPE

    private var _getSettingsBlutgruppeRoute = ScreenDefinition.SettingsBlutgruppe.route

    val getSettingsBlutgruppeRoute: String
        get() = _getSettingsBlutgruppeRoute


    // GENDER

    private var _showDialogGender = mutableStateOf(false)

    val getShowDialogGender: Boolean
        get() = _showDialogGender.value

    fun setShowDialogGender(value: Boolean) {
        _showDialogGender.value = value
    }

}