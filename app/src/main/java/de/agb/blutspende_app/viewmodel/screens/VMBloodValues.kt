package de.agb.blutspende_app.viewmodel.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.component.text.textComponent
import de.agb.blutspende_app.ui.theme.md_theme_dark_background
import de.agb.blutspende_app.ui.theme.md_theme_dark_secondary
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

    // Bottom Sheet
    // Datepicker
    private var _bottomSheetDatepickerVisible = mutableStateOf(false)

    val getBottomSheetDatepickerVisible: MutableState<Boolean>
        get() = _bottomSheetDatepickerVisible

    // Alert Dialog for adding Values
    private var _alertDialogAddValueVisible = mutableStateOf(false)

    val getAlertDialogAddValueVisible: MutableState<Boolean>
        get() = _alertDialogAddValueVisible


    fun thresholdLineStyle(
        averageSysValue: Float,
        systemInDarkTheme: Boolean,
        themeMode: State<Int>
    ): ThresholdLine {
        return ThresholdLine(
            thresholdValue = averageSysValue,
            lineComponent = ShapeComponent(
                color = when (themeMode.value) {
                    1 -> md_theme_dark_secondary.toArgb()
                    2 -> md_theme_dark_secondary.toArgb()
                    else -> when {
                        systemInDarkTheme -> md_theme_dark_secondary.toArgb()
                        else -> md_theme_dark_secondary.toArgb()
                    }

                }
            ),
            labelComponent = textComponent {
                color = when (themeMode.value) {
                    1 -> md_theme_dark_secondary.toArgb()
                    2 -> md_theme_dark_secondary.toArgb()
                    else -> when {
                        systemInDarkTheme -> md_theme_dark_secondary.toArgb()
                        else -> md_theme_dark_secondary.toArgb()
                    }

                }
            }
        )
    }


    fun chartStyle(systemInDarkTheme: Boolean, themeMode: State<Int>): ChartStyle {
        return ChartStyle(
            axis = ChartStyle.Axis(
                axisLabelColor = when (themeMode.value) {
                    1 -> md_theme_dark_secondary
                    2 -> md_theme_dark_secondary
                    else -> when {
                        systemInDarkTheme -> md_theme_dark_secondary
                        else -> md_theme_dark_secondary
                    }

                },
                axisGuidelineColor = when (themeMode.value) {
                    1 -> md_theme_dark_background
                    2 -> md_theme_dark_background
                    else -> when {
                        systemInDarkTheme -> md_theme_dark_background
                        else -> md_theme_dark_background
                    }

                },
                axisLineColor = when (themeMode.value) {
                    1 -> md_theme_dark_secondary
                    2 -> md_theme_dark_secondary
                    else -> when {
                        systemInDarkTheme -> md_theme_dark_secondary
                        else -> md_theme_dark_secondary
                    }

                },
            ),
            columnChart = ChartStyle.ColumnChart(
                listOf(
                    LineComponent(
                        Color(0xFF9C4238).toArgb(),
                        DefaultDimens.COLUMN_WIDTH,
                        Shapes.roundedCornerShape(DefaultDimens.COLUMN_ROUNDNESS_PERCENT),
                    )
                )
            ),
            marker = ChartStyle.Marker(),
            elevationOverlayColor = Color(0xFF9C4238),
            lineChart = ChartStyle.LineChart(
                lines = listOf(
                    LineChart.LineSpec(
                        lineColor = Color(0xFFC13020).toArgb(),
                        lineBackgroundShader = DynamicShaders.fromBrush(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFFC13020).copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                    Color(0xFFC13020).copy(com.patrykandpatrick.vico.core.DefaultAlpha.LINE_BACKGROUND_SHADER_END)
                                )
                            )
                        )
                    )
                )
            ),
        )
    }
}