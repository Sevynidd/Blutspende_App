package de.agb.blutspende_app.viewmodel.screens

import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.entryOf
import de.agb.blutspende_app.model.roomDatabase.BloodValuesState
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

    @OptIn(ExperimentalMaterial3Api::class)
    fun bloodvaluesChartEntries(
        displayType: Int,
        state: BloodValuesState,
        datesForXAxis: ArrayList<Long>,
        dateRangePickerState: DateRangePickerState
    ): List<ChartEntry> {
        return if (_selectedFilterText.value == _filterOptions[0]) {
            val size = if (state.bloodValuesList.size > 3) {
                3
            } else {
                state.bloodValuesList.size
            }

            for (i in 0..<size) {
                datesForXAxis.add(state.bloodValuesList[i].timestamp)
            }

            List(size) {
                entryOf(
                    it,
                    when (displayType) {
                        0 -> state.bloodValuesList[it].systolisch
                        1 -> state.bloodValuesList[it].diastolisch
                        2 -> state.bloodValuesList[it].puls
                        else -> 0
                    }
                )
            }

        } else {
            val idList = ArrayList<Int>()

            state.bloodValuesList.forEachIndexed { index, bloodValues ->
                if (bloodValues.timestamp in (dateRangePickerState.selectedStartDateMillis
                        ?: 0)..(dateRangePickerState.selectedEndDateMillis ?: 0)
                ) {
                    idList.add(index)
                }
            }

            for (i in 0..<idList.size) {
                datesForXAxis.add(state.bloodValuesList[idList[i]].timestamp)
            }

            List(idList.size) {
                entryOf(
                    it,
                    when (displayType) {
                        0 -> state.bloodValuesList[it].systolisch
                        1 -> state.bloodValuesList[it].diastolisch
                        2 -> state.bloodValuesList[it].puls
                        else -> 0
                    }
                )
            }

        }
    }
}