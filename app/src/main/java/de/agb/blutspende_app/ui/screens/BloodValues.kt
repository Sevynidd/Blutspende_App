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
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.runtime.mutableStateOf
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
import de.agb.blutspende_app.ui.theme.Blooddonation_AppTheme
import de.agb.blutspende_app.viewmodel.GlobalFunctions
import java.text.DateFormat.MEDIUM
import java.text.DateFormat.getDateInstance

@Composable
fun BloodValues(state: BloodValuesState, onEvent: (BloodValuesEvent) -> Unit) {
    Blooddonation_AppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
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
            val cardPadding = 12.dp
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(cardPadding),
                    text = "Die letzten 3 Blutspendewerte:"
                )

                state.bloodValuesList.forEach { blutwert ->
                    Text(
                        text = blutwert.blutwerteID.toString() + " " +
                                blutwert.systolisch + " Sys " +
                                blutwert.diastolisch + " Dia " +
                                blutwert.puls + " Puls " +
                                state.armsList[blutwert.fArmID].bezeichnung + " " +
                                state.typesList[blutwert.fTypID].blutspendeTyp
                    )
                }

            }

            Button(onClick = {
                onEvent(BloodValuesEvent.SetSystolic(120))
                onEvent(BloodValuesEvent.SetDiastolic(90))
                onEvent(BloodValuesEvent.SetHaemoglobin(13.5f))
                onEvent(BloodValuesEvent.SetPulse(70))
                onEvent(BloodValuesEvent.FArmID(0))
                onEvent(BloodValuesEvent.FTypID(0))
                onEvent(BloodValuesEvent.SaveBloodValues)
            }) {
                Text(text = "TestButton")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodValueFilter() {
    val globalFunctions: GlobalFunctions = viewModel()

    val options = listOf("Letzte 3 Blutwerte", "Blutwerte mit Datumsfilter")
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    Row {
        options.forEach { item ->
            Button(modifier = Modifier.padding(horizontal = 6.dp),
                colors = when (item == selectedOptionText) {
                    true -> ButtonDefaults.buttonColors()
                    false -> ButtonDefaults.filledTonalButtonColors()
                },
                onClick = { selectedOptionText = item }
            ) {
                Text(text = item)
            }
        }
    }

    Spacer(modifier = Modifier.size(18.dp))

    var bottomSheetVisible by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val dateRangePickerState = rememberDateRangePickerState()

    val sdf = getDateInstance(MEDIUM)

    LaunchedEffect(key1 = LocalLifecycleOwner.current) {
        if ((dateRangePickerState.selectedStartDateMillis == null) or (dateRangePickerState.selectedEndDateMillis == null)) {
            dateRangePickerState.setSelection(
                System.currentTimeMillis() - 604800000L, System.currentTimeMillis()
            )
        }
    }

    if (selectedOptionText == options[1]) {
        ClickableText(text = AnnotatedString(

            sdf.format(
                globalFunctions.millisToDate(
                    dateRangePickerState.selectedStartDateMillis ?: 0
                )
            ) + "  bis  " + sdf.format(
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