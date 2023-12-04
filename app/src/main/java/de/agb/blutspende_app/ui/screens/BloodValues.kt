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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
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
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.legend.legendItem
import com.patrykandpatrick.vico.compose.legend.verticalLegend
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.composed.plus
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.text.textComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.composed.ComposedChartEntryModelProducer
import de.agb.blutspende_app.R
import de.agb.blutspende_app.model.roomDatabase.BloodValues
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(state: BloodValuesState, onEvent: (BloodValuesEvent) -> Unit) {

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val dateRangePickerState = rememberDateRangePickerState()

        BloodValueFilter(dateRangePickerState)

        CardWithBloodValues(onEvent, state, dateRangePickerState)

        Spacer(modifier = Modifier.size(14.dp))

        BloodValuesDiagram(state, dateRangePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodValueFilter(dateRangePickerState: DateRangePickerState) {
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

    var bottomSheetDateRangepickerVisible by remember { vmBloodValues.getBottomSheetDateRangepickerVisible }
    val sheetStateDatepicker = rememberModalBottomSheetState()

    LaunchedEffect(key1 = LocalLifecycleOwner.current) {
        if ((dateRangePickerState.selectedStartDateMillis == null) or (dateRangePickerState.selectedEndDateMillis == null)) {
            dateRangePickerState.setSelection(
                // 604800000L is a week
                System.currentTimeMillis() - 604800000L,
                System.currentTimeMillis()
            )
        }
    }

    Spacer(modifier = Modifier.size(18.dp))

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
            onClick = {
                bottomSheetDateRangepickerVisible = bottomSheetDateRangepickerVisible.not()
            })
    }

    if (bottomSheetDateRangepickerVisible) {
        ModalBottomSheet(
            sheetState = sheetStateDatepicker,
            onDismissRequest = { bottomSheetDateRangepickerVisible = false }) {

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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardWithBloodValues(
    onEvent: (BloodValuesEvent) -> Unit,
    state: BloodValuesState,
    dateRangePickerState: DateRangePickerState
) {
    val vmBloodValues: VMBloodValues = viewModel()

    val alertDialogAddValueVisible = remember { vmBloodValues.getAlertDialogAddValueVisible }

    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = vmBloodValues.getSelectedFilterText.value,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )

        IconButton(modifier = Modifier.size(28.dp),
            onClick = {
                alertDialogAddValueVisible.value = true
            }) {
            Icon(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = "Add Value"
            )
        }
    }

    Spacer(modifier = Modifier.size(10.dp))

    if (alertDialogAddValueVisible.value) {
        AlertDialogForAddingOrEditingValues(alertDialogAddValueVisible, onEvent, false, null)
    }

    if (state.bloodValuesList.isNotEmpty()) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            AddBloodValuesToCard(state, onEvent, dateRangePickerState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogForAddingOrEditingValues(
    alertDialogVisible: MutableState<Boolean>,
    onEvent: (BloodValuesEvent) -> Unit,
    editMode: Boolean,
    prefillValues: List<String>?
) {
    val patternOnlyNumbers = remember { Regex("\\d+\$") }

    fun validateInt(text: String, mutableIsError: MutableState<Boolean>) {
        when {
            text == "00" -> mutableIsError.value = true
            text == "" -> mutableIsError.value = true
            text.matches(patternOnlyNumbers) -> mutableIsError.value = false
        }
    }


    val vmBloodValues: VMBloodValues = viewModel()
    val focusManager = LocalFocusManager.current

    var textSystolic by remember { mutableStateOf("") }
    val systolicIsError = remember { mutableStateOf(false) }
    var textDiastolic by remember { mutableStateOf("") }
    var textPuls by remember { mutableStateOf("") }
    var textHaemoglobin by remember { mutableStateOf("") }
    var idArm by remember { mutableIntStateOf(0) }
    var idDonationType by remember { mutableIntStateOf(0) }
    val datePickerState = rememberDatePickerState()

    if (editMode and !prefillValues.isNullOrEmpty()) {
        LaunchedEffect(key1 = LocalLifecycleOwner.current) {
            textSystolic = prefillValues?.get(1) ?: ""
            textDiastolic = prefillValues?.get(2) ?: ""
            textPuls = prefillValues?.get(3) ?: ""
            idArm = prefillValues?.get(4)?.toInt() ?: 0
            idDonationType = prefillValues?.get(5)?.toInt() ?: 0
            datePickerState.setSelection(
                prefillValues?.get(6)?.toLong() ?: System.currentTimeMillis()
            )
            textHaemoglobin = prefillValues?.get(7) ?: "0"
        }
    }

    AlertDialog(
        onDismissRequest = { alertDialogVisible.value = false },
        title = { Text("Neuen Blutwert hinzufügen") },
        text = {
            Column(Modifier.fillMaxWidth()) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Sys: ", modifier = Modifier.weight(1f))

                    TextField(
                        value = textSystolic, onValueChange = {
                            validateInt(it, systolicIsError)
                            if (!((it == "00") and
                                        (textSystolic == "0") and
                                        (textSystolic.length == 1))
                            ) {
                                if (it.isEmpty() || it.matches(patternOnlyNumbers)) {

                                    textSystolic = it

                                    if (textSystolic != "") {
                                        if (textSystolic.toInt() > 300) {
                                            textSystolic = "300"
                                        }
                                    }
                                }
                            }
                        }, modifier = Modifier.weight(2f),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword,
                            imeAction = ImeAction.Next
                        ),
                        isError = systolicIsError.value,
                        singleLine = true,
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )

                    Spacer(modifier = Modifier.size(12.dp))

                    Text("Dia: ", modifier = Modifier.weight(1f))

                    TextField(
                        value = textDiastolic, onValueChange = {
                            if (!((it == "00") and
                                        (textDiastolic == "0") and
                                        (textDiastolic.length == 1))
                            ) {
                                if (it.isEmpty() || it.matches(patternOnlyNumbers)) {

                                    textDiastolic = it

                                    if (textDiastolic != "") {
                                        if (textDiastolic.toInt() > 250) {
                                            textDiastolic = "250"
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

                    TextField(
                        value = textPuls, onValueChange = {
                            if (!((it == "00") and
                                        (textPuls == "0") and
                                        (textPuls.length == 1))
                            ) {
                                if (it.isEmpty() || it.matches(patternOnlyNumbers)) {

                                    textPuls = it

                                    if (textPuls != "") {
                                        if (textPuls.toInt() > 200) {
                                            textPuls = "200"
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

                    Spacer(modifier = Modifier.size(14.dp))

                    Text(
                        "Haemoglobin: ",
                        modifier = Modifier.weight(1f),
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.size(4.dp))

                    TextField(
                        value = textHaemoglobin, onValueChange = {
                            textHaemoglobin = it.trim()
                        }, modifier = Modifier.weight(2f),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                }

                Spacer(modifier = Modifier.size(20.dp))

                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Datum: ", modifier = Modifier.weight(1f))

                    val showDatePickerDialog = remember { mutableStateOf(false) }

                    TextField(
                        readOnly = true,
                        value = TextFieldValue(
                            vmBloodValues.dateFormat.format(
                                Date(
                                    datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                                )
                            )
                        ),
                        onValueChange = {},
                        trailingIcon = {
                            IconButton(onClick = {
                                showDatePickerDialog.value = true
                            }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Date"
                                )
                            }
                        },
                        modifier = Modifier.weight(2f)
                    )

                    if (showDatePickerDialog.value) {
                        DatePickerDialog(
                            onDismissRequest = { showDatePickerDialog.value = false },
                            confirmButton = {
                                TextButton(onClick = { showDatePickerDialog.value = false }) {
                                    Text("ok")
                                }
                            }) {
                            DatePicker(state = datePickerState)
                        }
                    }
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

                onEvent(BloodValuesEvent.SetSystolic(textSystolic.toInt()))
                onEvent(BloodValuesEvent.SetDiastolic(textDiastolic.toInt()))
                onEvent(BloodValuesEvent.SetHaemoglobin(textHaemoglobin.toFloat()))
                onEvent(BloodValuesEvent.SetPulse(textPuls.toInt()))
                onEvent(
                    BloodValuesEvent.SetTimestamp(
                        datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                    )
                )
                onEvent(BloodValuesEvent.SetFArmID(idArm))
                onEvent(BloodValuesEvent.SetFTypID(idDonationType))

                if (editMode) {
                    if (prefillValues != null) {
                        onEvent(BloodValuesEvent.SetID(prefillValues[0].toInt()))
                    }
                }

                onEvent(BloodValuesEvent.SaveBloodValues)

                alertDialogVisible.value = false
            }) {
                Text("Speichern")
            }
        },
        dismissButton = {
            TextButton(onClick = { alertDialogVisible.value = false }) {
                Text("Abbrechen")
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBloodValuesToCard(
    state: BloodValuesState,
    onEvent: (BloodValuesEvent) -> Unit,
    dateRangePickerState: DateRangePickerState
) {
    val vmBloodValues: VMBloodValues = viewModel()
    val alertDialogDeleteValueVisible = remember { vmBloodValues.getAlertDialogDeleteValueVisible }
    val cardPadding = 12.dp

    if (vmBloodValues.getSelectedFilterText.value == vmBloodValues.getFilterOptions[0]) {

        state.bloodValuesList.forEachIndexed { index, bloodValues ->
            if (index < 3) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = vmBloodValues.dateFormat.format(bloodValues.timestamp) + ": " +
                                bloodValues.systolisch + " zu " + bloodValues.diastolisch + " mit " +
                                bloodValues.puls + "er Puls\n" +
                                "Arm: " + state.armsList[bloodValues.fArmID].bezeichnung + "\n" +
                                "Typ: " + state.typesList[bloodValues.fTypID].blutspendeTyp,
                        modifier = Modifier
                            .padding(cardPadding)
                            .weight(3f)
                    )

                    Column(
                        Modifier
                            .weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val alertDialogVisible = remember { mutableStateOf(false) }

                        IconButton(modifier = Modifier
                            .size(22.dp),
                            onClick = {
                                alertDialogVisible.value = true
                            }) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit),
                                contentDescription = "Edit Value"
                            )
                        }

                        if (alertDialogVisible.value) {
                            AlertDialogForAddingOrEditingValues(
                                alertDialogVisible = alertDialogVisible,
                                onEvent = onEvent,
                                editMode = true,
                                prefillValues = listOf(
                                    bloodValues.blutwerteID.toString(),
                                    bloodValues.systolisch.toString(),
                                    bloodValues.diastolisch.toString(),
                                    bloodValues.puls.toString(),
                                    bloodValues.fArmID.toString(),
                                    bloodValues.fTypID.toString(),
                                    bloodValues.timestamp.toString(),
                                    bloodValues.haemoglobin.toString()
                                )
                            )
                        }

                        Spacer(modifier = Modifier.size(16.dp))

                        IconButton(modifier = Modifier
                            .size(24.dp),
                            onClick = {
                                alertDialogDeleteValueVisible.value = true
                            }) {
                            Icon(
                                painter = painterResource(id = R.drawable.minus),
                                contentDescription = "Delete Value"
                            )
                        }

                        if (alertDialogDeleteValueVisible.value) {
                            AlertDialog(
                                onDismissRequest = { alertDialogDeleteValueVisible.value = false },
                                title = { Text("Willst du den folgenden Datensatz wirklich löschen?") },
                                text = {
                                    Text(
                                        text = "Datum: " + vmBloodValues.dateFormat.format(
                                            bloodValues.timestamp
                                        ) + "\n" +
                                                "Systolisch: " + bloodValues.systolisch + "\n" +
                                                "Diastolisch: " + bloodValues.diastolisch + "\n" +
                                                "Puls: " + bloodValues.puls + "\n" +
                                                "Typ: " + state.typesList[bloodValues.fTypID].blutspendeTyp + "\n" +
                                                "Arm: " + state.armsList[bloodValues.fArmID].bezeichnung
                                    )
                                },
                                confirmButton = {
                                    TextButton(onClick = {
                                        onEvent(
                                            BloodValuesEvent.DeleteBloodValues(
                                                BloodValues(
                                                    blutwerteID = bloodValues.blutwerteID,
                                                    systolisch = bloodValues.systolisch,
                                                    diastolisch = bloodValues.diastolisch,
                                                    puls = bloodValues.puls,
                                                    haemoglobin = bloodValues.haemoglobin,
                                                    fArmID = bloodValues.fArmID,
                                                    fTypID = bloodValues.fTypID,
                                                    timestamp = bloodValues.timestamp
                                                )
                                            )
                                        )
                                        alertDialogDeleteValueVisible.value = false
                                    }) {
                                        Text("Ja")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = {
                                        alertDialogDeleteValueVisible.value = false
                                    }) {
                                        Text("Nein")
                                    }
                                })
                        }
                    }
                }

            }
        }
    } else {
        state.bloodValuesList.forEach { bloodValues ->
            if (bloodValues.timestamp in (dateRangePickerState.selectedStartDateMillis
                    ?: 0)..(dateRangePickerState.selectedEndDateMillis ?: 0)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = vmBloodValues.dateFormat.format(bloodValues.timestamp) + ": " +
                                bloodValues.systolisch + " zu " + bloodValues.diastolisch + " mit " +
                                bloodValues.puls + "er Puls\n" +
                                "Arm: " + state.armsList[bloodValues.fArmID].bezeichnung + "\n" +
                                "Typ: " + state.typesList[bloodValues.fTypID].blutspendeTyp,
                        modifier = Modifier
                            .padding(cardPadding)
                            .weight(3f)
                    )

                    Column(
                        Modifier
                            .weight(0.5f), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val alertDialogVisible = remember { mutableStateOf(false) }

                        IconButton(modifier = Modifier
                            .size(22.dp),
                            onClick = {
                                alertDialogVisible.value = true
                            }) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit),
                                contentDescription = "Edit Value"
                            )
                        }

                        if (alertDialogVisible.value) {
                            AlertDialogForAddingOrEditingValues(
                                alertDialogVisible = alertDialogVisible,
                                onEvent = onEvent,
                                editMode = true,
                                prefillValues = listOf(
                                    bloodValues.blutwerteID.toString(),
                                    bloodValues.systolisch.toString(),
                                    bloodValues.diastolisch.toString(),
                                    bloodValues.puls.toString(),
                                    bloodValues.fArmID.toString(),
                                    bloodValues.fTypID.toString(),
                                    bloodValues.timestamp.toString(),
                                    bloodValues.haemoglobin.toString()
                                )
                            )
                        }

                        Spacer(modifier = Modifier.size(16.dp))

                        IconButton(modifier = Modifier
                            .size(24.dp),
                            onClick = {
                                alertDialogDeleteValueVisible.value = true
                            }) {
                            Icon(
                                painter = painterResource(id = R.drawable.minus),
                                contentDescription = "Delete Value"
                            )
                        }

                        if (alertDialogDeleteValueVisible.value) {
                            AlertDialog(
                                onDismissRequest = { alertDialogDeleteValueVisible.value = false },
                                title = { Text("Willst du den folgenden Datensatz wirklich löschen?") },
                                text = {
                                    Text(
                                        text = "Datum: " + vmBloodValues.dateFormat.format(
                                            bloodValues.timestamp
                                        ) + "\n" +
                                                "Systolisch: " + bloodValues.systolisch + "\n" +
                                                "Diastolisch: " + bloodValues.diastolisch + "\n" +
                                                "Puls: " + bloodValues.puls + "\n" +
                                                "Typ: " + state.typesList[bloodValues.fTypID].blutspendeTyp + "\n" +
                                                "Arm: " + state.armsList[bloodValues.fArmID].bezeichnung
                                    )
                                },
                                confirmButton = {
                                    TextButton(onClick = {
                                        onEvent(
                                            BloodValuesEvent.DeleteBloodValues(
                                                BloodValues(
                                                    blutwerteID = bloodValues.blutwerteID,
                                                    systolisch = bloodValues.systolisch,
                                                    diastolisch = bloodValues.diastolisch,
                                                    puls = bloodValues.puls,
                                                    haemoglobin = bloodValues.haemoglobin,
                                                    fArmID = bloodValues.fArmID,
                                                    fTypID = bloodValues.fTypID,
                                                    timestamp = bloodValues.timestamp
                                                )
                                            )
                                        )
                                        alertDialogDeleteValueVisible.value = false
                                    }) {
                                        Text("Ja")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = {
                                        alertDialogDeleteValueVisible.value = false
                                    }) {
                                        Text("Nein")
                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodValuesDiagram(state: BloodValuesState, dateRangePickerState: DateRangePickerState) {
    val vmBloodValues: VMBloodValues = viewModel()

    if (state.bloodValuesList.isNotEmpty()) {

        AnimatedVisibility(state.bloodValuesList.size > 1) {

            Column(Modifier.fillMaxWidth()) {
                Text("Systolisch/Diastolisch")

                Spacer(modifier = Modifier.size(12.dp))

                Card(modifier = Modifier.fillMaxWidth()) {

                    val datesForXAxis = ArrayList<Long>()

                    val composedChartEntryModelProducer =
                        ComposedChartEntryModelProducer.build {
                            add(
                                vmBloodValues.bloodvaluesChartEntries(
                                    0,
                                    state,
                                    datesForXAxis,
                                    dateRangePickerState
                                )
                            )
                            add(
                                vmBloodValues.bloodvaluesChartEntries(
                                    1,
                                    state,
                                    datesForXAxis,
                                    dateRangePickerState
                                )
                            )
                        }

                    val lineChartSys = lineChart(
                        lines = vmBloodValues.lineChartColors(Color(0xFFA42315))
                    )
                    val lineChartDia = lineChart(
                        lines = vmBloodValues.lineChartColors(Color(0xFF0C2B7E))
                    )

                    val cardPadding = 12.dp
                    Chart(
                        chart = remember(
                            lineChartSys,
                            lineChartDia
                        ) { lineChartSys + lineChartDia },
                        chartModelProducer = composedChartEntryModelProducer,
                        startAxis = rememberStartAxis(
                            itemPlacer = AxisItemPlacer.Vertical.default(
                                maxItemCount = 4,
                                shiftTopLines = false
                            )
                        ),
                        bottomAxis = rememberBottomAxis(
                            valueFormatter = { value, _ ->
                                if (datesForXAxis.size > 0) {
                                    vmBloodValues.dateFormat.format(datesForXAxis[value.toInt()])
                                } else {
                                    "Kein Inhalt"
                                }
                            }
                        ),
                        legend = verticalLegend(
                            items = listOf(
                                legendItem(
                                    icon = shapeComponent(
                                        shape = Shapes.pillShape,
                                        color = Color(0xFFA42315)
                                    ),
                                    label = textComponent {
                                        color = Color(0xFFC65B50).toArgb()
                                    },
                                    labelText = "Systolisch"
                                ),
                                legendItem(
                                    icon = shapeComponent(
                                        shape = Shapes.pillShape,
                                        color = Color(0xFF0C2B7E)
                                    ),
                                    label = textComponent {
                                        color = Color(0xFF6086EB).toArgb()
                                    },
                                    labelText = "Diastolisch"
                                )
                            ),
                            iconSize = 8.dp,
                            iconPadding = 10.dp
                        ),
                        modifier = Modifier.padding(cardPadding)
                    )
                }

                Spacer(modifier = Modifier.size(12.dp))

                Text("Puls")

                Spacer(modifier = Modifier.size(12.dp))

                Card(modifier = Modifier.fillMaxWidth()) {

                    val datesForXAxis = ArrayList<Long>()

                    val pulseThresholdLineValue = remember { mutableFloatStateOf(0f) }

                    val pulseThresholdLine = ThresholdLine(
                        thresholdValue = pulseThresholdLineValue.floatValue
                    )

                    val chartEntryModelProducer =
                        ChartEntryModelProducer(
                            vmBloodValues.bloodvaluesChartEntries(
                                2,
                                state,
                                datesForXAxis,
                                dateRangePickerState,
                                pulseThresholdLineValue
                            )
                        )

                    val lineChartPulse = lineChart(
                        lines = vmBloodValues.lineChartColors(Color(0xFFA42315)),
                        decorations = listOf(pulseThresholdLine)
                    )

                    val cardPadding = 12.dp
                    Chart(
                        chart = lineChartPulse,
                        chartModelProducer = chartEntryModelProducer,
                        startAxis = rememberStartAxis(
                            itemPlacer = AxisItemPlacer.Vertical.default(
                                maxItemCount = 4,
                                shiftTopLines = false
                            )
                        ),
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

                Spacer(modifier = Modifier.size(12.dp))

                Text("Hämoglobin")

                Spacer(modifier = Modifier.size(12.dp))

                Card(modifier = Modifier.fillMaxWidth()) {

                    val datesForXAxis = ArrayList<Long>()

                    val haemoglobinThresholdLineValue = remember { mutableFloatStateOf(0f) }

                    val haemoglobinThresholdLine = ThresholdLine(
                        thresholdValue = haemoglobinThresholdLineValue.floatValue
                    )

                    val chartEntryModelProducer =
                        ChartEntryModelProducer(
                            vmBloodValues.bloodvaluesChartEntries(
                                3,
                                state,
                                datesForXAxis,
                                dateRangePickerState,
                                haemoglobinThresholdLineValue
                            )
                        )

                    val lineChartHaemoglobin = lineChart(
                        lines = vmBloodValues.lineChartColors(Color(0xFFA42315)),
                        decorations = listOf(haemoglobinThresholdLine)
                    )

                    val cardPadding = 12.dp
                    Chart(
                        chart = lineChartHaemoglobin,
                        chartModelProducer = chartEntryModelProducer,
                        startAxis = rememberStartAxis(
                            itemPlacer = AxisItemPlacer.Vertical.default(
                                maxItemCount = 4,
                                shiftTopLines = false
                            )
                        ),
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

                Spacer(Modifier.size(28.dp))
            }
        }
    }
}