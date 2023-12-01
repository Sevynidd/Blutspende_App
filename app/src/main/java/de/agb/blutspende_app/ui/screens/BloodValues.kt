package de.agb.blutspende_app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf
import de.agb.blutspende_app.R
import de.agb.blutspende_app.model.roomDatabase.BloodValuesEvent
import de.agb.blutspende_app.model.roomDatabase.BloodValuesState
import de.agb.blutspende_app.ui.theme.BlooddonationAppTheme
import de.agb.blutspende_app.viewmodel.GlobalFunctions
import de.agb.blutspende_app.viewmodel.screens.VMBloodValues
import java.util.Date

@Composable
fun BloodValues(state: BloodValuesState, onEvent: (BloodValuesEvent) -> Unit) {
    BlooddonationAppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
            ) {

                Content(state, onEvent)

            }
        }
    }
}

@Composable
fun Content(state: BloodValuesState, onEvent: (BloodValuesEvent) -> Unit) {

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        BloodValueFilter(state, onEvent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodValueFilter(state: BloodValuesState, onEvent: (BloodValuesEvent) -> Unit) {
    val globalFunctions: GlobalFunctions = viewModel()
    val vmBloodValues: VMBloodValues = viewModel()

    var selectedOptionText by remember { vmBloodValues.getSelectedFilterText }

    Row {
        vmBloodValues.getFilterOptions.forEach { item ->
            Button(modifier = Modifier.padding(horizontal = 6.dp),
                colors = when (item == selectedOptionText) {
                    true -> ButtonDefaults.buttonColors()
                    false -> ButtonDefaults.filledTonalButtonColors()
                },
                onClick = {
                    if (selectedOptionText != item) {
                        selectedOptionText = item
                    }
                }
            ) {
                Text(text = item)
            }
        }
    }

    Spacer(modifier = Modifier.size(18.dp))

    var bottomSheetDatepickerVisible by remember { vmBloodValues.getBottomSheetDatepickerVisible }
    val sheetStateDatepicker = rememberModalBottomSheetState()
    val dateRangePickerState = rememberDateRangePickerState()

    LaunchedEffect(key1 = LocalLifecycleOwner.current) {
        if ((dateRangePickerState.selectedStartDateMillis == null) or (dateRangePickerState.selectedEndDateMillis == null)) {
            dateRangePickerState.setSelection(
                // 604800000L is a week
                System.currentTimeMillis() - 604800000L,
                System.currentTimeMillis()
            )
        }
    }

    if (selectedOptionText == vmBloodValues.getFilterOptions[1]) {
        ClickableText(text = AnnotatedString(

            vmBloodValues.dateFormat.format(
                globalFunctions.millisToDate(
                    dateRangePickerState.selectedStartDateMillis
                        ?: (System.currentTimeMillis() - 604800000L)


                )
            ) + "  bis  " + vmBloodValues.dateFormat.format(
                globalFunctions.millisToDate(
                    dateRangePickerState.selectedEndDateMillis ?: (System.currentTimeMillis())
                )
            )
        ),
            style = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 15.sp),
            onClick = { bottomSheetDatepickerVisible = bottomSheetDatepickerVisible.not() })
    }

    if (bottomSheetDatepickerVisible) {

        ModalBottomSheet(
            sheetState = sheetStateDatepicker,
            onDismissRequest = { bottomSheetDatepickerVisible = false }) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
            ) {
                DateRangePicker(
                    state = dateRangePickerState, dateFormatter = DatePickerFormatter(
                        "dd.MM.yyyy", "dd.MM.yyyy", "dd.MM.yyyy"
                    )
                )

            }
        }

    }

    var alertDialogAddValueVisible by remember { vmBloodValues.getAlertDialogAddValueVisible }

    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = vmBloodValues.getSelectedFilterText.value,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )

        IconButton(modifier = Modifier.size(28.dp),
            onClick = {
                alertDialogAddValueVisible = true
            }) {
            Icon(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = "Add Value"
            )
        }
    }

    if (alertDialogAddValueVisible) {

        var textSystolic by remember { mutableStateOf(TextFieldValue("")) }

        AlertDialog(
            onDismissRequest = { alertDialogAddValueVisible.not() },
            title = { Text("Neuen Blutwert hinzufügen") },
            text = {
                Column(Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("Systolisch: ", modifier = Modifier.weight(1f))

                        val patternOnlyNumbers = remember { Regex("\\d+\$") }
                        TextField(
                            value = textSystolic, onValueChange = {
                                if (!((it.text == "0") and
                                            (textSystolic.text == "0") and
                                            (textSystolic.text.length == 1))) {
                                    if (it.text.isEmpty() || it.text.matches(patternOnlyNumbers)) {

                                        textSystolic = it

                                        if (textSystolic.text != "") {
                                            if (textSystolic.text.toInt() > 300) {
                                                textSystolic = TextFieldValue("300")
                                            }
                                        }
                                    }
                                }
                            }, modifier = Modifier.weight(2f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { alertDialogAddValueVisible = false }) {
                    Text("Speichern")
                }
            },
            dismissButton = {
                TextButton(onClick = { alertDialogAddValueVisible = false }) {
                    Text("Abbrechen")
                }
            })

    }

    Spacer(modifier = Modifier.size(14.dp))

    val cardPadding = 12.dp

    if (state.bloodValuesList.isNotEmpty()) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            if (vmBloodValues.getSelectedFilterText.value == vmBloodValues.getFilterOptions[0]) {

                state.bloodValuesList.forEachIndexed { index, bloodValues ->
                    if (index < 3) {
                        Text(
                            text = bloodValues.blutwerteID.toString() + " " +
                                    bloodValues.systolisch + " Sys " +
                                    bloodValues.diastolisch + " Dia " +
                                    bloodValues.puls + " Puls " +
                                    state.armsList[bloodValues.fArmID].bezeichnung + " " +
                                    state.typesList[bloodValues.fTypID].blutspendeTyp + "\n" +
                                    //dateFormat.format(blutwert.timestamp) + " " + timeFormat.format(blutwert.timestamp) +
                                    Date(bloodValues.timestamp),
                            modifier = Modifier.padding(cardPadding)
                        )
                    }
                }
            } else {
                state.bloodValuesList.forEach { blutwert ->
                    if (blutwert.timestamp in (dateRangePickerState.selectedStartDateMillis
                            ?: 0)..(dateRangePickerState.selectedEndDateMillis ?: 0)
                    ) {
                        Text(
                            text = blutwert.blutwerteID.toString() + " " +
                                    blutwert.systolisch + " Sys " +
                                    blutwert.diastolisch + " Dia " +
                                    blutwert.puls + " Puls " +
                                    state.armsList[blutwert.fArmID].bezeichnung + " " +
                                    state.typesList[blutwert.fTypID].blutspendeTyp + "\n" +
                                    //dateFormat.format(blutwert.timestamp) + " " + timeFormat.format(blutwert.timestamp) +
                                    Date(blutwert.timestamp),
                            modifier = Modifier.padding(cardPadding)
                        )
                    }
                }
            }
        }
    }

    Button(onClick = {
        onEvent(BloodValuesEvent.SetSystolic(120))
        onEvent(BloodValuesEvent.SetDiastolic(90))
        onEvent(BloodValuesEvent.SetHaemoglobin(13.5f))
        onEvent(BloodValuesEvent.SetPulse(70))
        onEvent(BloodValuesEvent.SetTimestamp(System.currentTimeMillis()))
        onEvent(BloodValuesEvent.FArmID(0))
        onEvent(BloodValuesEvent.FTypID(0))
        onEvent(BloodValuesEvent.SaveBloodValues)
    }) {
        Text(text = "TestButton")
    }

    AnimatedVisibility((state.bloodValuesList.isNotEmpty()) and (state.bloodValuesList.size > 1)) {
        Column(Modifier.fillMaxWidth()) {
            Text("Blutwerte Chart")

            Card(modifier = Modifier.fillMaxWidth()) {

                val datesForXAxis = ArrayList<Long>()
                var averageSysValue = 0f

                fun bloodvaluesChartEntries() =
                    if (vmBloodValues.getSelectedFilterText.value == vmBloodValues.getFilterOptions[0]) {
                        val size = if (state.bloodValuesList.size > 3) {
                            3
                        } else {
                            state.bloodValuesList.size
                        }

                        averageSysValue = 0f

                        for (i in 0..<size) {
                            datesForXAxis.add(state.bloodValuesList[i].timestamp)
                            averageSysValue += state.bloodValuesList[i].systolisch
                        }

                        averageSysValue /= size

                        List(size) {
                            entryOf(
                                it,
                                state.bloodValuesList[it].systolisch
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

                        averageSysValue = 0f

                        for (i in 0..<idList.size) {
                            datesForXAxis.add(state.bloodValuesList[idList[i]].timestamp)
                            averageSysValue += state.bloodValuesList[idList[i]].systolisch
                        }

                        averageSysValue /= idList.size

                        List(idList.size) {
                            entryOf(
                                it,
                                state.bloodValuesList[idList[it]].systolisch
                            )
                        }
                    }

                val chartEntryModelProducer = ChartEntryModelProducer(bloodvaluesChartEntries())

                val thresholdLine = remember { ThresholdLine(thresholdValue = averageSysValue) }

                Chart(
                    chart = lineChart(
                        decorations = listOf(thresholdLine)
                    ),
                    chartModelProducer = chartEntryModelProducer,
                    startAxis = rememberStartAxis(),
                    bottomAxis = rememberBottomAxis(
                        valueFormatter = { value, _ ->
                            if (datesForXAxis.size > 0) {
                                vmBloodValues.dateFormat.format(datesForXAxis[value.toInt()])
                            } else {
                                "Kein Inhalt"
                            }
                        }
                    ),
                    modifier = Modifier.padding(cardPadding)
                )
            }
        }
    }
}