package de.agb.blutspende_app.viewmodel.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import de.agb.blutspende_app.R
import de.agb.blutspende_app.model.ScreenDefinition

class SettingsViewModel : ViewModel() {
    // DARSTELLUNG

    private var _showDialogDarstellung = mutableStateOf(false)
    private val _imageIdsDarstellung =
        listOf(R.drawable.ui_system, R.drawable.ui_hell, R.drawable.ui_dunkel)

    val getShowDialogDarstellung: Boolean
        get() = _showDialogDarstellung.value

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

    // SPRACHE

    private var _showDialogSprache = mutableStateOf(false)

    val getShowDialogSprache: Boolean
        get() = _showDialogSprache.value

    fun setShowDialogSprache(value: Boolean) {
        _showDialogSprache.value = value
    }

}