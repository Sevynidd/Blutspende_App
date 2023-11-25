package de.agb.blutspende_app.ui.screens

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.agb.blutspende_app.model.roomDatabase.BloodValuesEvent
import de.agb.blutspende_app.model.roomDatabase.BloodValuesState
import de.agb.blutspende_app.ui.theme.BlooddonationAppTheme
import de.agb.blutspende_app.viewmodel.GlobalFunctions
import de.agb.blutspende_app.viewmodel.screens.VMBloodValues
import java.text.DateFormat.MEDIUM
import java.text.DateFormat.getDateInstance
import java.text.DateFormat.getTimeInstance
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

        BloodValueFilter()

        Column(
            modifier = Modifier.padding(top = 14.dp, bottom = 14.dp)
        ) {

            ContentBloodValues(state, onEvent)

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodValueFilter() {
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

    var bottomSheetVisible by remember { vmBloodValues.getBottomSheetVisible }
    val sheetState = rememberModalBottomSheetState()
    val dateRangePickerState = rememberDateRangePickerState()

    val dateFormat = getDateInstance(MEDIUM)

    LaunchedEffect(key1 = LocalLifecycleOwner.current) {
        if ((dateRangePickerState.selectedStartDateMillis == null) or (dateRangePickerState.selectedEndDateMillis == null)) {
            dateRangePickerState.setSelection(
                // 604800000L is a week
                System.currentTimeMillis() - 604800000L, System.currentTimeMillis()
            )
        }
    }

    if (selectedOptionText == vmBloodValues.getFilterOptions[1]) {
        ClickableText(text = AnnotatedString(

            dateFormat.format(
                globalFunctions.millisToDate(
                    dateRangePickerState.selectedStartDateMillis ?: 0
                )
            ) + "  bis  " + dateFormat.format(
                globalFunctions.millisToDate(
                    dateRangePickerState.selectedEndDateMillis ?: 0
                )
            )
        ),
            style = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 15.sp),
            onClick = { bottomSheetVisible = bottomSheetVisible.not() })
    }

    if (bottomSheetVisible) {

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { bottomSheetVisible = false }) {

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

@Composable
fun ContentBloodValues(state: BloodValuesState, onEvent: (BloodValuesEvent) -> Unit) {
    val vmBloodValues: VMBloodValues = viewModel()
    val vmGlobalFunctions: GlobalFunctions = viewModel()

    Text(
        text = vmBloodValues.getSelectedFilterText.value,
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(modifier = Modifier.size(14.dp))

    if (state.bloodValuesList.isNotEmpty()) {
        val cardPadding = 12.dp
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            val dateFormat = getDateInstance(MEDIUM)
            val timeFormat = getTimeInstance(MEDIUM)

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
                // TODO Recompose on startdate/enddate change of DatePicker
                state.bloodValuesList.forEach { blutwert ->
                    if (blutwert.timestamp in (System.currentTimeMillis() - 604800000L)..(System.currentTimeMillis()))
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
}