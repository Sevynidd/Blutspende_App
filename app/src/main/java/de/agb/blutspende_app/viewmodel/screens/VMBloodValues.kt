package de.agb.blutspende_app.viewmodel.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.text.DateFormat

class VMBloodValues : ViewModel() {
    // Filter
    private val _filterOptions = listOf("Letzte 3 Blutwerte", "Blutwerte mit Datumsfilter")
    private var _selectedFilterText = mutableStateOf(getFilterOptions[0])

    val getFilterOptions: List<String>
        get() = _filterOptions

    val getSelectedFilterText: MutableState<String>
        get() = _selectedFilterText

    // Bottom Sheet
    // Datepicker
    private var _bottomSheetDatepickerVisible = mutableStateOf(false)

    val getBottomSheetDatepickerVisible: MutableState<Boolean>
        get() = _bottomSheetDatepickerVisible

    // Add Value
    private var _alertDialogAddValueVisible = mutableStateOf(false)

    val getAlertDialogAddValueVisible: MutableState<Boolean>
        get() = _alertDialogAddValueVisible

    val dateFormat: DateFormat
        get() = DateFormat.getDateInstance(DateFormat.MEDIUM)


}