package de.agb.blutspende_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import de.agb.blutspende_app.R
import de.agb.blutspende_app.model.InterfaceSettingsItem
import de.agb.blutspende_app.model.ScreenDefinition
import de.agb.blutspende_app.ui.theme.Blutspende_AppTheme
import de.agb.blutspende_app.viewmodel.DatastoreViewModel
import de.agb.blutspende_app.viewmodel.settings.SettingsViewModel

@Composable
fun Settings(navController: NavHostController) {
    Blutspende_AppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .safeContentPadding()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    DarstellungItem()

                    Spacer(Modifier.size(10.dp))

                    BlutgruppeItem(navController)

                    Spacer(Modifier.size(10.dp))

                    GeschlechtItem()

                }
            }
        }
    }
}

@Composable
fun DefinitionSettingsItem(item: InterfaceSettingsItem) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { item.onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.wrapContentWidth()) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = item.title,
                modifier = Modifier
                    .size(52.dp)
                    .padding(end = 20.dp)
            )
        }

        Column(Modifier.wrapContentWidth()) {
            Text(
                text = item.title,
                style = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 15.sp)
            )
            item.subTitle?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 12.sp
                    )
                )
            }
        }

        if (item.rightArrowButtonVisible) {
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "ArrowRight")
            }
        }
    }
}

@Composable
fun DarstellungItem() {
    val datastoreViewModel: DatastoreViewModel = viewModel()
    val settingsViewModel: SettingsViewModel = viewModel()

    val selectedOption by datastoreViewModel.getThemeMode.collectAsState(0)

    DefinitionSettingsItem(
        object : InterfaceSettingsItem {
            override val title = "Darstellung"
            override val subTitle = "System / Hell / Dunkel"
            override val icon = R.drawable.paint_brush

            override val onClick = {
                settingsViewModel.setShowDialogDarstellung(settingsViewModel.getShowDialogDarstellung.not())
            }
        }
    )

    if (settingsViewModel.getShowDialogDarstellung) {
        AlertDialog(
            onDismissRequest = { settingsViewModel.setShowDialogDarstellung(false) },
            title = { Text("Darstellung") },
            text = {
                Row {
                    settingsViewModel.getRadioOptionsDarstellung.forEach { text ->
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .selectable(
                                    selected = (text == settingsViewModel.getRadioOptionsDarstellung[selectedOption]),
                                    onClick = {
                                        datastoreViewModel.saveThemeToDataStore(
                                            settingsViewModel.getRadioOptionsDarstellung.indexOf(
                                                text
                                            )
                                        )
                                    }
                                ),
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(
                                    id = settingsViewModel.getImageIdsDarstellung[
                                        settingsViewModel.getRadioOptionsDarstellung.indexOf(
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
                                selected = (text == settingsViewModel.getRadioOptionsDarstellung[selectedOption]),
                                onClick = {
                                    datastoreViewModel.saveThemeToDataStore(
                                        settingsViewModel.getRadioOptionsDarstellung.indexOf(
                                            text
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    settingsViewModel.setShowDialogDarstellung(false)
                }) {
                    Text("ok".uppercase())
                }
            },
        )
    }
}

@Composable
fun BlutgruppeItem(navController: NavHostController) {
    val settingsViewModel: SettingsViewModel = viewModel()

    DefinitionSettingsItem(
        object : InterfaceSettingsItem {
            override val title = "Blutgruppe"
            override val subTitle = "ABO, Rhesus, Rhesuskomplex & Kell"
            override val icon = R.drawable.blood_drop
            override val onClick = {
                navController.navigate(settingsViewModel.getSettingsBlutgruppeRoute)
            }
            override val rightArrowButtonVisible = true
        }
    )
}

@Composable
fun GeschlechtItem() {
    DefinitionSettingsItem(
        object : InterfaceSettingsItem {
            override val title = "Geschlecht"
            override val icon = R.drawable.blutspendedienst_owl_icon
            override val onClick = {
                //darstellungViewModel.setShowDialog(darstellungViewModel.getShowDialog.not())
            }

        }
    )
}
