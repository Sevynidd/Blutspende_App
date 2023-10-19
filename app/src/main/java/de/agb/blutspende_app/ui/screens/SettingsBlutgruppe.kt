package de.agb.blutspende_app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import de.agb.blutspende_app.R
import de.agb.blutspende_app.ui.theme.Blutspende_AppTheme
import de.agb.blutspende_app.viewmodel.GlobalFunctions
import de.agb.blutspende_app.viewmodel.VMDatastore
import de.agb.blutspende_app.viewmodel.screens.settings.VMSettingsBlutgruppe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun SettingsBlutgruppe(navController: NavController) {
    Blutspende_AppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
            ) {
                Blutgruppe()

                Spacer(modifier = Modifier.size(12.dp))

                Rhesus()

                Spacer(modifier = Modifier.size(12.dp))

                Rhesuscomplex()

                Spacer(modifier = Modifier.size(12.dp))

                Kell(navController = navController)

                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }
}

@Composable
fun Blutgruppe() {
    val vm: VMSettingsBlutgruppe = viewModel()
    val vmDatastore: VMDatastore = viewModel()
    val globalFunctions: GlobalFunctions = viewModel()

    val showAB0SystemHint = vm.getIsVisibleAB0System

    Column(
        Modifier.fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "ABO-System", fontSize = 22.sp)

            IconButton(onClick = { vm.setIsVisibleAB0System(showAB0SystemHint.not()) }) {
                Icon(Icons.Rounded.Info, contentDescription = "InfoBlutgruppe")
            }
        }

        AnimatedVisibility(showAB0SystemHint) {
            Card(
                modifier = Modifier.clickable {
                    vm.setIsVisibleAB0System(showAB0SystemHint.not())
                }
            ) {
                val cardItemPadding = Modifier.padding(12.dp)
                Text(
                    text = stringResource(id = R.string.AB0System_Help_Title),
                    modifier = cardItemPadding
                )
                Text(
                    text = stringResource(id = R.string.AB0System_Help_Text1),
                    modifier = cardItemPadding
                )
                Image(
                    painter = painterResource(id = R.drawable.unterscheidung_der_blutgruppen),
                    contentDescription = "Unterscheidung Blutgruppen",
                    modifier = cardItemPadding
                )
                Text(
                    text = stringResource(id = R.string.AB0System_Help_Text2),
                    modifier = cardItemPadding
                )
            }
        }

        if (showAB0SystemHint) {
            Spacer(modifier = Modifier.size(16.dp))
        }

        Column(modifier = Modifier.fillMaxWidth()) {

            val selectedOption by vmDatastore.getBlutgruppe.collectAsState(0)

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
                                    vmDatastore.saveBlutgruppeToDataStore(i)
                                }
                            ),
                            horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(
                                    id = globalFunctions.getBloodbagIconFromBlutgruppeID(i)
                                ),
                                contentDescription = globalFunctions.getBlutgruppeAsString(i)
                            )
                            Text(
                                text = globalFunctions.getBlutgruppeAsString(i),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            RadioButton(
                                selected = (i == selectedOption),
                                onClick = {
                                    vmDatastore.saveBlutgruppeToDataStore(i)
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
fun Rhesus() {
    val vm: VMSettingsBlutgruppe = viewModel()
    val vmDatastore: VMDatastore = viewModel()
    val globalFunctions: GlobalFunctions = viewModel()

    val showRhesusHint = vm.getIsVisibleRhesus

    Column(
        Modifier.fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Rhesus", fontSize = 22.sp)

            IconButton(onClick = { vm.setIsVisibleRhesus(showRhesusHint.not()) }) {
                Icon(Icons.Rounded.Info, contentDescription = "InfoRhesus")
            }
        }

        AnimatedVisibility(showRhesusHint) {
            Card(
                modifier = Modifier.clickable {
                    vm.setIsVisibleRhesus(showRhesusHint.not())
                }
            ) {
                val cardItemPadding = Modifier.padding(12.dp)
                Text(
                    text = stringResource(id = R.string.AB0System_Help_Title),
                    modifier = cardItemPadding
                )
                Text(
                    text = stringResource(id = R.string.AB0System_Help_Text1),
                    modifier = cardItemPadding
                )
                Image(
                    painter = painterResource(id = R.drawable.unterscheidung_der_blutgruppen),
                    contentDescription = "Unterscheidung Blutgruppen",
                    modifier = cardItemPadding
                )
                Text(
                    text = stringResource(id = R.string.AB0System_Help_Text2),
                    modifier = cardItemPadding
                )
            }
        }

        if (showRhesusHint) {
            Spacer(modifier = Modifier.size(16.dp))
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
fun Rhesuscomplex() {
    val vm: VMSettingsBlutgruppe = viewModel()
    val vmDatastore: VMDatastore = viewModel()

    val showRhesuscomplexHint = vm.getIsVisibleRhesuscomplex

    Column(
        Modifier.fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.rhesuskomplex), fontSize = 22.sp)

            IconButton(onClick = { vm.setIsVisibleRhesuscomplex(showRhesuscomplexHint.not()) }) {
                Icon(Icons.Rounded.Info, contentDescription = "InfoRhesuskomplex")
            }
        }

        AnimatedVisibility(showRhesuscomplexHint) {
            Card(
                modifier = Modifier.clickable {
                    vm.setIsVisibleRhesuscomplex(showRhesuscomplexHint.not())
                }
            ) {
                val cardItemPadding = Modifier.padding(12.dp)
                Text(
                    text = stringResource(id = R.string.AB0System_Help_Title),
                    modifier = cardItemPadding
                )
                Text(
                    text = stringResource(id = R.string.AB0System_Help_Text1),
                    modifier = cardItemPadding
                )
                Image(
                    painter = painterResource(id = R.drawable.unterscheidung_der_blutgruppen),
                    contentDescription = "Unterscheidung Blutgruppen",
                    modifier = cardItemPadding
                )
                Text(
                    text = stringResource(id = R.string.AB0System_Help_Text2),
                    modifier = cardItemPadding
                )
            }
        }

        if (showRhesuscomplexHint) {
            Spacer(modifier = Modifier.size(16.dp))
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
                    placeholder = { Text("Bsp.: CcD.ee") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            vmDatastore.saveRhesuskomplexToDataStore(entryC)
                        }
                    )
                )

                val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

                LaunchedEffect(key1 = true) {
                    lifecycleOwner.lifecycleScope.launch {
                        vmDatastore.getRhesuskomplex.flowWithLifecycle(lifecycleOwner.lifecycle)
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
    val vm: VMSettingsBlutgruppe = viewModel()
    val vmDatastore: VMDatastore = viewModel()
    val globalFunctions: GlobalFunctions = viewModel()

    val showKellHint = vm.getIsVisibleKell

    Column(
        Modifier.fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Kell", fontSize = 22.sp)

            IconButton(onClick = {
                vmDatastore.saveBlutspendeInfoURLToDataStore("https://www.blutspende.de/magazin/von-a-bis-0/kell-system-in-der-blutgruppenbestimmung")
                navController.navigate(globalFunctions.getScreenRouteSettingsBlutgruppeWebview)
            }) {
                Icon(Icons.Rounded.Info, contentDescription = "InfoKell")
            }
        }


        if (showKellHint) {
            Spacer(modifier = Modifier.size(16.dp))
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