package de.agb.blutspende_app.viewmodel.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import java.text.DateFormat

class VMBloodValues : ViewModel() {
    // Filter
    private val _filterOptions = listOf("Letzte 3 Blutwerte", "Blutwerte mit Datumsfilter")
    private var _selectedFilterText = mutableStateOf(getFilterOptions[0])

    val dateFormat: DateFormat
        get() = DateFormat.getDateInstance(DateFormat.MEDIUM)

    val getFilterOptions: List<String>
        get() = _filterOptions

    val getSelectedFilterText: MutableState<String>
        get() = _selectedFilterText

    // Bottom Sheet DateRangepicker
    private var _bottomSheetDateRangepickerVisible = mutableStateOf(false)

    val getBottomSheetDateRangepickerVisible: MutableState<Boolean>
        get() = _bottomSheetDateRangepickerVisible

    // Alert Dialog for adding Values
    private var _alertDialogAddValueVisible = mutableStateOf(false)

    val getAlertDialogAddValueVisible: MutableState<Boolean>
        get() = _alertDialogAddValueVisible

    // Alert Dialog for deleting Values
    private var _alertDialogDeleteValueVisible = mutableStateOf(false)

    val getAlertDialogDeleteValueVisible: MutableState<Boolean>
        get() = _alertDialogDeleteValueVisible

    // Bottom Sheet Datepicker
    private var _bottomSheetDatepickerVisible = mutableStateOf(false)

    val getBottomSheetDatepickerVisible: MutableState<Boolean>
        get() = _bottomSheetDatepickerVisible


    fun lineChartColors(color: Color): List<LineChart.LineSpec> {
        return listOf(
            LineChart.LineSpec(
                lineColor = color.toArgb(),
                lineBackgroundShader = DynamicShaders.fromBrush(
                    Brush.verticalGradient(
                        listOf(
                            color.copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                            color.copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_END)
                        )
                    )
                )
            )
        )
    }
}