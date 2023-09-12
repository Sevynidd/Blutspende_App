package agb.loehne.blutspende_app.screens

import agb.loehne.blutspende_app.R
import agb.loehne.blutspende_app.datastore.StoreSettingsViewModel
import agb.loehne.blutspende_app.ui.theme.Blutspende_AppTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatPaint
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
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    DialogDarstellung()
                    Spacer(modifier = Modifier.size(20.dp))
                    DialogBlutgruppe()
                }
            }
        }
    }
}

@Composable
fun DialogDarstellung() {
    var showDialogDarstellung by remember { mutableStateOf(false) }
    val viewModel: StoreSettingsViewModel = viewModel()

    Row(Modifier.fillMaxWidth()) {
        Column {
            Icon(
                Icons.Default.FormatPaint,
                "Einstellungen",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .size(46.dp)
                    .padding(end = 20.dp)
            )
        }

        Column(Modifier.fillMaxWidth()) {
            ClickableText(
                text = AnnotatedString("Darstellung\nSystem / Hell / Dunkel"),

                onClick = {
                    showDialogDarstellung = showDialogDarstellung.not()
                },
                style = TextStyle(MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            )

            val radioOptions = listOf("System", "Hell", "Dunkel")
            val uiImages =
                listOf(R.drawable.ui_system, R.drawable.ui_hell, R.drawable.ui_dunkel)
            val (selectedOption, onOptionSelected) = remember {
                mutableStateOf(
                    radioOptions[0]
                )
            }
            if (showDialogDarstellung) {
                AlertDialog(
                    onDismissRequest = { showDialogDarstellung = false },
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
                            showDialogDarstellung = false
                        }) {
                            Text("ok".uppercase())
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialogDarstellung = false }) {
                            Text("Cancel".uppercase())
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun DialogBlutgruppe() {
    var showDialogBlutgruppe by remember { mutableStateOf(false) }
    //val viewModel: StoreSettingsViewModel = viewModel()

    Row(Modifier.fillMaxWidth()) {
        Column {
            Icon(
                painter = painterResource(id = R.drawable.blood_drop),
                "Einstellungen",
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .size(46.dp)
                    .padding(end = 20.dp)
            )
        }

        Column(Modifier.fillMaxWidth()) {
            ClickableText(
                text = AnnotatedString("Blutgruppe\nAB0, Rhesus, Rhesuskomplex & Kell"),
                onClick = {
                    showDialogBlutgruppe = showDialogBlutgruppe.not()
                },
                style = TextStyle(MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            )

            val radioOptionsBlutgruppe = listOf("O", "A", "B", "AB")
            val uiImagesBlutgruppe =
                listOf(R.drawable.man1, R.drawable.man2, R.drawable.woman1, R.drawable.woman2)
            val (selectedOption, onOptionSelected) = remember {
                mutableStateOf(
                    radioOptionsBlutgruppe[0]
                )
            }
            if (showDialogBlutgruppe) {
                AlertDialog(
                    onDismissRequest = { showDialogBlutgruppe = false },
                    title = { Text("Blutgruppe") },
                    text = {
                        Row {
                            radioOptionsBlutgruppe.forEach { text ->
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
                                            id = uiImagesBlutgruppe[radioOptionsBlutgruppe.indexOf(
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
                            //viewModel.saveToDataStore(radioOptions.indexOf(selectedOption))
                            showDialogBlutgruppe = false
                        }) {
                            Text("ok".uppercase())
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialogBlutgruppe = false }) {
                            Text("Cancel".uppercase())
                        }
                    },
                )
            }
        }
    }
}
