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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
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

    var selectedFilterOptionText by remember { vmBloodValues.getSelectedFilterText }

    Row {
        vmBloodValues.getFilterOptions.forEach { item ->
            Button(modifier = Modifier.padding(horizontal = 6.dp),
                colors = when (item == selectedFilterOptionText) {
                    true -> ButtonDefaults.buttonColors()
                    false -> ButtonDefaults.filledTonalButtonColors()
                },
                onClick = {
                    if (selectedFilterOptionText != item) {
                        selectedFilterOptionText = item
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

    if (selectedFilterOptionText == vmBloodValues.getFilterOptions[1]) {
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

        val focusManager = LocalFocusManager.current

        var textSystolic by remember { mutableStateOf(TextFieldValue("")) }
        var textDiastolic by remember { mutableStateOf(TextFieldValue("")) }
        var textPuls by remember { mutableStateOf(TextFieldValue("")) }
        var idArm by remember { mutableIntStateOf(0) }
        var idDonationType by remember { mutableIntStateOf(0) }

        AlertDialog(
            onDismissRequest = { alertDialogAddValueVisible.not() },
            properties = DialogProperties(
                dismissOnClickOutside = true
            ),
            title = { Text("Neuen Blutwert hinzufügen") },
            text = {
                Column(Modifier.fillMaxWidth()) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("Systolisch: ", modifier = Modifier.weight(1f))

                        val patternOnlyNumbers = remember { Regex("\\d+\$") }
                        TextField(
                            value = textSystolic, onValueChange = {
                                if (!((it.text == "00") and
                                            (textSystolic.text == "0") and
                                            (textSystolic.text.length == 1))
                                ) {
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
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.NumberPassword,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                        )
                    }

                    Spacer(modifier = Modifier.size(20.dp))

                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("Diastolisch: ", modifier = Modifier.weight(1f))

                        val patternOnlyNumbers = remember { Regex("\\d+\$") }
                        TextField(
                            value = textDiastolic, onValueChange = {
                                if (!((it.text == "00") and
                                            (textDiastolic.text == "0") and
                                            (textDiastolic.text.length == 1))
                                ) {
                                    if (it.text.isEmpty() || it.text.matches(patternOnlyNumbers)) {

                                        textDiastolic = it

                                        if (textDiastolic.text != "") {
                                            if (textDiastolic.text.toInt() > 250) {
                                                textDiastolic = TextFieldValue("250")
                                            }
                                        }
                                    }
                                }
                            }, modifier = Modifier.weight(2f),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.NumberPassword,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                        )
                    }

                    Spacer(modifier = Modifier.size(20.dp))

                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("Puls: ", modifier = Modifier.weight(1f))

                        val patternOnlyNumbers = remember { Regex("\\d+\$") }
                        TextField(
                            value = textPuls, onValueChange = {
                                if (!((it.text == "00") and
                                            (textPuls.text == "0") and
                                            (textPuls.text.length == 1))
                                ) {
                                    if (it.text.isEmpty() || it.text.matches(patternOnlyNumbers)) {

                                        textPuls = it

                                        if (textPuls.text != "") {
                                            if (textPuls.text.toInt() > 200) {
                                                textPuls = TextFieldValue("200")
                                            }
                                        }
                                    }
                                }
                            }, modifier = Modifier.weight(2f),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.NumberPassword,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                        )
                    }

                    Spacer(modifier = Modifier.size(20.dp))

                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("Arm: ", modifier = Modifier.weight(1f))

                        val armItems = listOf("Linker Arm", "Rechter Arm")
                        var armComboboxExpanded by remember { mutableStateOf(false) }

                        ExposedDropdownMenuBox(
                            expanded = armComboboxExpanded,
                            onExpandedChange = {
                                armComboboxExpanded = armComboboxExpanded.not()
                            },
                            modifier = Modifier.weight(2f)
                        ) {

                            TextField(
                                readOnly = true,
                                value = armItems[idArm], onValueChange = {},
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = armComboboxExpanded
                                    )
                                }, modifier = Modifier.menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = armComboboxExpanded,
                                onDismissRequest = { armComboboxExpanded = false }) {
                                armItems.forEachIndexed { index, item ->
                                    DropdownMenuItem(
                                        text = { Text(text = item) },
                                        onClick = {
                                            idArm = index
                                            armComboboxExpanded = false
                                        })
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.size(20.dp))

                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text("Spendetyp: ", modifier = Modifier.weight(1f))

                        val donationTypeText = listOf("Vollblut", "Plasma", "Thrombozyten")
                        var donationTypeComboboxExpanded by remember { mutableStateOf(false) }

                        ExposedDropdownMenuBox(
                            expanded = donationTypeComboboxExpanded,
                            onExpandedChange = {
                                donationTypeComboboxExpanded = donationTypeComboboxExpanded.not()
                            },
                            modifier = Modifier.weight(2f)
                        ) {

                            TextField(
                                readOnly = true,
                                value = donationTypeText[idDonationType], onValueChange = {},
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = donationTypeComboboxExpanded
                                    )
                                }, modifier = Modifier.menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = donationTypeComboboxExpanded,
                                onDismissRequest = { donationTypeComboboxExpanded = false }) {
                                donationTypeText.forEachIndexed { index, item ->
                                    DropdownMenuItem(
                                        text = { Text(text = item) },
                                        onClick = {
                                            idDonationType = index
                                            donationTypeComboboxExpanded = false
                                        })
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onEvent(BloodValuesEvent.SetSystolic(textSystolic.text.toInt()))
                    onEvent(BloodValuesEvent.SetDiastolic(textDiastolic.text.toInt()))
                    onEvent(BloodValuesEvent.SetHaemoglobin(13.5f))
                    onEvent(BloodValuesEvent.SetPulse(textPuls.text.toInt()))
                    onEvent(BloodValuesEvent.SetTimestamp(System.currentTimeMillis()))
                    onEvent(BloodValuesEvent.FArmID(idArm))
                    onEvent(BloodValuesEvent.FTypID(idDonationType))
                    onEvent(BloodValuesEvent.SaveBloodValues)

                    alertDialogAddValueVisible = false
                }) {
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
                            text = vmBloodValues.dateFormat.format(bloodValues.timestamp) + ": " +
                                    bloodValues.systolisch + " zu " + bloodValues.diastolisch + " mit " +
                                    bloodValues.puls + "er Puls\n" +
                                    "Typ: " + state.typesList[bloodValues.fTypID].blutspendeTyp + "\n" +
                                    "Arm: " + state.armsList[bloodValues.fArmID].bezeichnung,
                            modifier = Modifier.padding(cardPadding)
                        )
                    }
                }
            } else {
                state.bloodValuesList.forEach { bloodValues ->
                    if (bloodValues.timestamp in (dateRangePickerState.selectedStartDateMillis
                            ?: 0)..(dateRangePickerState.selectedEndDateMillis ?: 0)
                    ) {
                        Text(
                            text = vmBloodValues.dateFormat.format(bloodValues.timestamp) + ": " +
                                    bloodValues.systolisch + " zu " + bloodValues.diastolisch + " mit " +
                                    bloodValues.puls + "er Puls\n" +
                                    "Typ: " + state.typesList[bloodValues.fTypID].blutspendeTyp + "\n" +
                                    "Arm: " + state.armsList[bloodValues.fArmID].bezeichnung,
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
            Text("Blutwerte Liniendiagramm")

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