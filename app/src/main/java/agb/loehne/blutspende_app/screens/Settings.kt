package agb.loehne.blutspende_app.screens

import agb.loehne.blutspende_app.R
import agb.loehne.blutspende_app.datastore.StoreSettingsViewModel
import agb.loehne.blutspende_app.ui.theme.Blutspende_AppTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Einstellungen() {
    Blutspende_AppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .safeDrawingPadding()
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    //ExposedDropdownMenuBoxDarkMode()
                    DarstellungDialog()
                }
            }
        }
    }
}

@Composable
fun DarstellungDialog() {
    var showDialog by remember { mutableStateOf(false) }
    val viewModel: StoreSettingsViewModel = viewModel()

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = CenterVertically) {

        Column {
            Icon(
                painter = painterResource(id = R.drawable.paint_brush),
                "Einstellungen",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .size(46.dp)
                    .padding(end = 20.dp)
            )
        }

        Column {
            ClickableText(
                text = AnnotatedString("Darstellung\nSystem / Hell / Dunkel"),
                style = TextStyle(MaterialTheme.colorScheme.primary),
                onClick = {
                    showDialog = showDialog.not()
                })

            val radioOptions = listOf("System", "Hell", "Dunkel")
            val uiImages =
                listOf(R.drawable.ui_system, R.drawable.ui_hell, R.drawable.ui_dunkel)
            val (selectedOption, onOptionSelected) = remember {
                mutableStateOf(
                    radioOptions[0]
                )
            }
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Darstellung") },
                    text = {
                        Row {
                            radioOptions.forEach { text ->
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .selectable(
                                            selected = (text == selectedOption),
                                            onClick = {
                                                onOptionSelected(text)
                                            }
                                        ),
                                    horizontalAlignment = CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(
                                            id = uiImages[radioOptions.indexOf(
                                                text
                                            )]
                                        ),
                                        contentDescription = text
                                    )
                                    Text(
                                        text = text,
                                        style = TextStyle(MaterialTheme.colorScheme.primary),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                    RadioButton(
                                        selected = (text == selectedOption),
                                        onClick = { onOptionSelected(text) }
                                    )
                                }
                            }
                        }

                    },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.saveToDataStore(radioOptions.indexOf(selectedOption))
                            showDialog = false
                        }) {
                            Text("ok".uppercase())
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancel".uppercase())
                        }
                    },
                )
            }
        }
    }
}