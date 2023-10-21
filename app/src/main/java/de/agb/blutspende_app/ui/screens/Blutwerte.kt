package de.agb.blutspende_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.agb.blutspende_app.ui.theme.Blutspende_AppTheme
import java.text.DateFormat.MEDIUM
import java.text.DateFormat.getDateInstance
import java.util.Date

@Composable
fun Blutwerte() {
    Blutspende_AppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {

                Content()

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content() {
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        Text(text = "Filter")

        Spacer(modifier = Modifier.size(16.dp))

        var bottomSheetVisible by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()
        val dateRangePickerState = rememberDateRangePickerState()

        val sdf = getDateInstance(MEDIUM)

        ClickableText(
            text = AnnotatedString(
                if (dateRangePickerState.selectedStartDateMillis == null) {
                    "NaN"
                } else {
                    sdf.format(
                        Date(
                            dateRangePickerState.selectedStartDateMillis ?: 0
                        )
                    )
                } + "  bis  " +
                        if (dateRangePickerState.selectedEndDateMillis == null) {
                            "NaN"
                        } else {
                            sdf.format(
                                Date(
                                    dateRangePickerState.selectedEndDateMillis ?: 0
                                )
                            )
                        }
            ),
            style = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 15.sp),
            onClick = { bottomSheetVisible = bottomSheetVisible.not() })


        if (bottomSheetVisible) {

            ModalBottomSheet(sheetState = sheetState,
                onDismissRequest = { bottomSheetVisible = false }) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                ) {
                    DateRangePicker(
                        state = dateRangePickerState,
                        dateFormatter = DatePickerFormatter(
                            "dd.MM.yyyy",
                            "dd.MM.yyyy",
                            "dd.MM.yyyy"
                        )
                    )

                }
            }

        }

        Column(
            modifier = Modifier.padding(top = 14.dp, bottom = 14.dp)
        ) {
            val cardPadding = 12.dp
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(cardPadding),
                    text = "Die letzten 3 Blutspendewerte:"
                )


            }
        }
    }
}