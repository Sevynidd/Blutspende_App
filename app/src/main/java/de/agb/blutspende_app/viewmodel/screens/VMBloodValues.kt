package de.agb.blutspende_app.viewmodel.screens

import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class VMBloodValues : ViewModel() {
    // Filter
    private val _filterOptions = listOf("Letzte 3 Blutwerte", "Blutwerte mit Datumsfilter")
    private var _selectedFilterText = mutableStateOf(getFilterOptions[0])

    val getFilterOptions: List<String>
        get() = _filterOptions

    val getSelectedFilterText: MutableState<String>
        get() = _selectedFilterText

    // Bottom Sheet

    private var _bottomSheetVisible = mutableStateOf(false)

    val getBottomSheetVisible: MutableState<Boolean>
        get() = _bottomSheetVisible
}