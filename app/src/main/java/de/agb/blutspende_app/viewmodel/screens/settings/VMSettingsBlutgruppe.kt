package de.agb.blutspende_app.viewmodel.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class VMSettingsBlutgruppe : ViewModel() {
    private var _isVisibleAB0System = mutableStateOf(false)

    val getIsVisibleAB0System: Boolean
        get() = _isVisibleAB0System.value

    fun setIsVisibleAB0System(value: Boolean) {
        _isVisibleAB0System.value = value
    }


    private var _isVisibleRhesus = mutableStateOf(false)

    val getIsVisibleRhesus: Boolean
        get() = _isVisibleRhesus.value

    fun setIsVisibleRhesus(value: Boolean) {
        _isVisibleRhesus.value = value
    }


    private var _isVisibleRhesuscomplex = mutableStateOf(false)

    val getIsVisibleRhesuscomplex: Boolean
        get() = _isVisibleRhesuscomplex.value

    fun setIsVisibleRhesuscomplex(value: Boolean) {
        _isVisibleRhesuscomplex.value = value
    }


    private var _isVisibleKell = mutableStateOf(false)

    val getIsVisibleKell: Boolean
        get() = _isVisibleKell.value

    fun setIsVisibleKell(value: Boolean) {
        _isVisibleKell.value = value
    }

}