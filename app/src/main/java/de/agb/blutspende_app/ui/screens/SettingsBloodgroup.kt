package de.agb.blutspende_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import de.agb.blutspende_app.R
import de.agb.blutspende_app.ui.theme.BlooddonationAppTheme
import de.agb.blutspende_app.viewmodel.GlobalFunctions
import de.agb.blutspende_app.viewmodel.VMDatastore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun SettingsBloodgroup(navController: NavController) {
    BlooddonationAppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
            ) {
                Bloodgroup(navController = navController)

                Spacer(modifier = Modifier.size(12.dp))

                Rhesus(navController = navController)

                Spacer(modifier = Modifier.size(12.dp))

                Rhesuscomplex(navController = navController)

                Spacer(modifier = Modifier.size(12.dp))

                Kell(navController = navController)

                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }
}

@Composable
fun Bloodgroup(navController: NavController) {
    val vmDatastore: VMDatastore = viewModel()
    val globalFunctions: GlobalFunctions = viewModel()

    Column(
        Modifier.fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "ABO-System", style = MaterialTheme.typography.titleMedium)

            IconButton(onClick = {
                vmDatastore.saveBlooddonationInfoURLToDataStore("https://www.blutspenden.de/rund-ums-blut/blutgruppen/")
                navController.navigate(globalFunctions.getScreenRouteSettingsBloodgroupWebview)
            }) {
                Icon(Icons.Rounded.Info, contentDescription = "InfoBlutgruppe")
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {

            val selectedOption by vmDatastore.getBloodgroup.collectAsState(0)

            Card {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (i in 0..3) {
                        Column(Modifier
                            .weight(1f)
                            .selectable(
                                selected = (i == selectedOption),
                                onClick = {
                                    vmDatastore.saveBloodgroupToDataStore(i)
                                }
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(
                                    id = globalFunctions.getBloodbagIconFromBloodgroupID(i)
                                ),
                                contentDescription = globalFunctions.getBloodgroupAsString(i)
                            )
                            Text(
                                text = globalFunctions.getBloodgroupAsString(i),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            RadioButton(
                                selected = (i == selectedOption),
                                onClick = {
                                    vmDatastore.saveBloodgroupToDataStore(i)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Rhesus(navController: NavController) {
    val vmDatastore: VMDatastore = viewModel()
    val globalFunctions: GlobalFunctions = viewModel()

    Column(
        Modifier.fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Rhesus", style = MaterialTheme.typography.titleMedium)

            IconButton(onClick = {
                vmDatastore.saveBlooddonationInfoURLToDataStore("https://www.studysmarter.de/schule/biologie/genetik/rhesus-system/")
                navController.navigate(globalFunctions.getScreenRouteSettingsBloodgroupWebview)
            }) {
                Icon(Icons.Rounded.Info, contentDescription = "InfoRhesus")
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {

            val selectedOption by vmDatastore.getRhesus.collectAsState(true)

            Card {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (i in 0..1) {
                        Column(Modifier
                            .weight(1f)
                            .selectable(
                                selected = ((i != 0) == selectedOption),
                                onClick = {
                                    vmDatastore.saveRhesusToDataStore((i != 0))
                                }
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(
                                    id = when (i) {
                                        0 -> R.drawable.blood_rhesus_negative
                                        1 -> R.drawable.blood_rhesus_positive
                                        else -> R.drawable.blood_rhesus_positive
                                    }
                                ),
                                contentDescription = globalFunctions.getRhesusAsString((i != 0))
                            )
                            Text(
                                text = globalFunctions.getRhesusAsString((i != 0)),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            RadioButton(
                                selected = ((i != 0) == selectedOption),
                                onClick = {
                                    vmDatastore.saveRhesusToDataStore((i != 0))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Rhesuscomplex(navController: NavController) {
    val vmDatastore: VMDatastore = viewModel()
    val globalFunctions: GlobalFunctions = viewModel()

    Column(
        Modifier.fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.rhesuskomplex),
                style = MaterialTheme.typography.titleMedium
            )

            IconButton(onClick = {
                vmDatastore.saveBlooddonationInfoURLToDataStore("https://www.studysmarter.de/schule/biologie/genetik/rhesus-system/")
                navController.navigate(globalFunctions.getScreenRouteSettingsBloodgroupWebview)
            }) {
                Icon(Icons.Rounded.Info, contentDescription = "InfoRhesuskomplex")
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {

            Card {
                val entry =
                    MutableStateFlow("Init")
                val entryC by entry.collectAsState()


                val keyboardController = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current

                TextField(
                    value = entryC,
                    onValueChange = { entry.value = it },
                    singleLine = true,
                    placeholder = { Text("Ex.: CcD.ee") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            vmDatastore.saveRhesuscomplexToDataStore(entryC)
                        }
                    )
                )

                val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

                LaunchedEffect(key1 = true) {
                    lifecycleOwner.lifecycleScope.launch {
                        vmDatastore.getRhesuscomplex.flowWithLifecycle(lifecycleOwner.lifecycle)
                            .collect {
                                entry.value = it
                            }
                    }
                }
            }
        }
    }
}

@Composable
fun Kell(navController: NavController) {
    val vmDatastore: VMDatastore = viewModel()
    val globalFunctions: GlobalFunctions = viewModel()

    Column(
        Modifier.fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Kell", style = MaterialTheme.typography.titleMedium)

            IconButton(onClick = {
                vmDatastore.saveBlooddonationInfoURLToDataStore("https://www.blutspende.de/magazin/von-a-bis-0/kell-system-in-der-blutgruppenbestimmung")
                navController.navigate(globalFunctions.getScreenRouteSettingsBloodgroupWebview)
            }) {
                Icon(Icons.Rounded.Info, contentDescription = "InfoKell")
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {

            val selectedOption by vmDatastore.getKell.collectAsState(true)

            Card {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (i in 0..1) {
                        Column(Modifier
                            .weight(1f)
                            .selectable(
                                selected = ((i != 0) == selectedOption),
                                onClick = {
                                    vmDatastore.saveKellToDataStore((i != 0))
                                }
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(
                                    id = when (i) {
                                        0 -> R.drawable.blood_rhesus_negative
                                        1 -> R.drawable.blood_rhesus_positive
                                        else -> R.drawable.blood_rhesus_positive
                                    }
                                ),
                                contentDescription = globalFunctions.getRhesusAsString((i != 0))
                            )
                            Text(
                                text = globalFunctions.getRhesusAsString((i != 0)),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            RadioButton(
                                selected = ((i != 0) == selectedOption),
                                onClick = {
                                    vmDatastore.saveKellToDataStore((i != 0))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}