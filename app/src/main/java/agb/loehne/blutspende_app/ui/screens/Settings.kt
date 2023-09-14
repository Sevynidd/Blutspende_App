package agb.loehne.blutspende_app.ui.screens

import agb.loehne.blutspende_app.R
import agb.loehne.blutspende_app.model.ScreenDefinition
import agb.loehne.blutspende_app.ui.theme.Blutspende_AppTheme
import agb.loehne.blutspende_app.viewmodel.DatastoreViewModel
import agb.loehne.blutspende_app.viewmodel.settings.DarstellungViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun Settings(navController: NavHostController) {
    Blutspende_AppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val datastoreViewModel: DatastoreViewModel = viewModel()

                    DialogDarstellung(datastoreViewModel)

                    Spacer(modifier = Modifier.size(20.dp))

                    DialogBlutgruppe(navController)
                }
            }
        }
    }
}

@Composable
fun DialogDarstellung(
    datastoreViewModel: DatastoreViewModel
) {
    val darstellungViewModel: DarstellungViewModel = viewModel()

    val showDialogDarstellung = darstellungViewModel.getShowDialog
    val selectedOption by datastoreViewModel.getThemeMode.collectAsState(0)
    val radioOptions = darstellungViewModel.getRadioOptions
    val images = darstellungViewModel.getImageIds

    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                darstellungViewModel.setShowDialog(showDialogDarstellung.not())
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.paint_brush),
                "DarstellungIcon",
                modifier = Modifier
                    .size(54.dp)
                    .padding(end = 20.dp)
            )
        }

        Column(Modifier.fillMaxWidth()) {
            //Titel
            Text(
                text = "Darstellung",
                style = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 15.sp),
                modifier = Modifier.fillMaxWidth()
            )
            //Untertitel
            Text(
                text = "System / Hell / Dunkel",
                style = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp),
                modifier = Modifier.fillMaxWidth()
            )

            if (showDialogDarstellung) {
                AlertDialog(
                    onDismissRequest = { darstellungViewModel.setShowDialog(false) },
                    title = { Text("Darstellung") },
                    text = {
                        Row {
                            darstellungViewModel.getRadioOptions.forEach { text ->
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .selectable(
                                            selected = (text == radioOptions[selectedOption]),
                                            onClick = {
                                                datastoreViewModel.saveThemeToDataStore(
                                                    radioOptions.indexOf(
                                                        text
                                                    )
                                                )
                                            }
                                        ),
                                    horizontalAlignment = CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(
                                            id = images[radioOptions.indexOf(
                                                text
                                            )]
                                        ),
                                        contentDescription = text
                                    )
                                    Text(
                                        text = text,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                    RadioButton(
                                        selected = (text == radioOptions[selectedOption]),
                                        onClick = {
                                            datastoreViewModel.saveThemeToDataStore(
                                                radioOptions.indexOf(text)
                                            )
                                        }
                                    )
                                }
                            }
                        }

                    },
                    confirmButton = {
                        TextButton(onClick = {
                            darstellungViewModel.setShowDialog(false)
                        }) {
                            Text("ok".uppercase())
                        }
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogBlutgruppe(navController: NavHostController) {

    var showDialogBlutgruppe by remember { mutableStateOf(false) }
    val viewModel: DatastoreViewModel = viewModel()

    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                //showDialogBlutgruppe = showDialogBlutgruppe.not()
                navController.navigate(ScreenDefinition.SettingsBlutgruppe.route)
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Icon(
                painter = painterResource(id = R.drawable.blood_drop),
                "BlutgruppeIcon",
                modifier = Modifier
                    .size(54.dp)
                    .padding(end = 20.dp)
            )
        }

        Column(Modifier.fillMaxWidth()) {
            //Titel
            Text(
                text = "Blutgruppe",
                style = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 15.sp),
                modifier = Modifier.fillMaxWidth()
            )
            //Untertitel
            Text(
                text = "AB0, Rhesus, Rhesuskomplex & Kell",
                style = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp),
                modifier = Modifier.fillMaxWidth()
            )

            val optionsBlutgruppe = listOf("O", "A", "B", "AB")
            val (selectedOptionBlutgruppe, onOptionSelectedBlutgruppe) = remember {
                mutableStateOf(
                    optionsBlutgruppe[0]
                )
            }
            if (showDialogBlutgruppe) {
                AlertDialog(
                    onDismissRequest = { showDialogBlutgruppe = false },
                    title = { Text("Blutgruppe") },
                    text = {

                        Column(Modifier.fillMaxWidth()) {
                            // Blutgruppe
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                var expandedDropdownMenuBoxBlutgruppe by remember {
                                    mutableStateOf(
                                        false
                                    )
                                }

                                Text(text = "Blutgruppe")

                                Spacer(modifier = Modifier.size(10.dp))

                                ExposedDropdownMenuBox(
                                    expanded = expandedDropdownMenuBoxBlutgruppe,
                                    onExpandedChange = {
                                        expandedDropdownMenuBoxBlutgruppe =
                                            expandedDropdownMenuBoxBlutgruppe.not()
                                    }
                                ) {
                                    TextField(
                                        value = selectedOptionBlutgruppe,
                                        onValueChange = {},
                                        readOnly = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = expandedDropdownMenuBoxBlutgruppe
                                            )
                                        },
                                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                        modifier = Modifier.menuAnchor()
                                    )

                                    ExposedDropdownMenu(
                                        expanded = expandedDropdownMenuBoxBlutgruppe,
                                        onDismissRequest = {
                                            expandedDropdownMenuBoxBlutgruppe = false
                                        }) {
                                        optionsBlutgruppe.forEach { item ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        text = item,
                                                        style = TextStyle(color = MaterialTheme.colorScheme.onBackground)
                                                    )
                                                },
                                                onClick = {
                                                    onOptionSelectedBlutgruppe(item)
                                                    expandedDropdownMenuBoxBlutgruppe = false
                                                })
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.size(10.dp))

                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = "InfoBlutgruppe",
                                    modifier = Modifier.clickable {
                                        //Toast.makeText(cont, "Test", Toast.LENGTH_SHORT).show()
                                    })
                            }

                            Spacer(Modifier.size(20.dp))

                            // Rhesusfaktor
                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Text(text = "Rhesusfaktor")

                                Spacer(modifier = Modifier.size(10.dp))

                                val checkedState = remember { mutableStateOf(true) }
                                Switch(
                                    checked = checkedState.value,
                                    onCheckedChange = { checkedState.value = it },
                                    thumbContent = {
                                        when (checkedState.value) {
                                            true -> Icon(
                                                painter = painterResource(id = R.drawable.plus),
                                                contentDescription = "positiv",
                                                modifier = Modifier.size(14.dp)
                                            )

                                            else -> Icon(
                                                painter = painterResource(id = R.drawable.minus),
                                                contentDescription = "negativ",
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.saveBlutgruppeToDataStore(
                                optionsBlutgruppe.indexOf(
                                    selectedOptionBlutgruppe
                                )
                            )
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
