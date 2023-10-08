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
import androidx.compose.material3.Divider
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import de.agb.blutspende_app.R
import de.agb.blutspende_app.model.InterfaceSettingsItem
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

                    Text(text = stringResource(id = R.string.settings_general), fontSize = 18.sp)

                    Spacer(Modifier.size(10.dp))

                    DarstellungItem()

                    Spacer(Modifier.size(10.dp))

                    GeschlechtItem()


                    Spacer(Modifier.size(10.dp))
                    Divider()
                    Spacer(Modifier.size(10.dp))


                    Text(text = stringResource(id = R.string.blood_donation), fontSize = 18.sp)

                    Spacer(Modifier.size(10.dp))

                    BlutgruppeItem(navController)
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
            override val title = stringResource(id = R.string.theme)
            override val subTitle =
                stringResource(id = R.string.theme_system) + " / " +
                        stringResource(id = R.string.theme_light) + " / " +
                        stringResource(id = R.string.theme_dark)
            override val icon = R.drawable.paint_brush

            override val onClick = {
                settingsViewModel.setShowDialogDarstellung(settingsViewModel.getShowDialogDarstellung.not())
            }
        }
    )

    if (settingsViewModel.getShowDialogDarstellung) {
        val radioOptions = listOf(
            stringResource(id = R.string.theme_system),
            stringResource(id = R.string.theme_light),
            stringResource(id = R.string.theme_dark)
        )
        AlertDialog(
            onDismissRequest = { settingsViewModel.setShowDialogDarstellung(false) },
            title = { Text(stringResource(id = R.string.theme)) },
            text = {
                Row {
                    for (i in 0..2) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .selectable(
                                    selected = (i == selectedOption),
                                    onClick = {
                                        datastoreViewModel.saveThemeToDataStore(i)
                                    }
                                ),
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(
                                    id = settingsViewModel.getImageIdsDarstellung[i]
                                ),
                                contentDescription = radioOptions[i]
                            )
                            Text(
                                text = radioOptions[i],
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            RadioButton(
                                selected = (i == selectedOption),
                                onClick = {
                                    datastoreViewModel.saveThemeToDataStore(i)
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
    val viewModel: SettingsViewModel = viewModel()

    DefinitionSettingsItem(
        object : InterfaceSettingsItem {
            override val title = stringResource(id = R.string.bloodgroup)
            override val subTitle =
                "ABO, Rhesus, " + stringResource(id = R.string.rhesuskomplex) + " & Kell"
            override val icon = R.drawable.blood_drop
            override val onClick = {
                navController.navigate(viewModel.getSettingsBlutgruppeRoute)
            }
            override val rightArrowButtonVisible = true
        }
    )
}

@Composable
fun GeschlechtItem() {
    val viewModel: SettingsViewModel = viewModel()
    val datastoreViewModel: DatastoreViewModel = viewModel()

    val selectedOption by datastoreViewModel.getGender.collectAsState(false)

    DefinitionSettingsItem(
        object : InterfaceSettingsItem {
            override val title = stringResource(id = R.string.gender)
            override val icon = R.drawable.gender
            override val onClick = {
                viewModel.setShowDialogGender(viewModel.getShowDialogGender.not())
            }
        }
    )

    if (viewModel.getShowDialogGender) {
        val radioOptions = listOf(
            stringResource(id = R.string.male),
            stringResource(id = R.string.female)
        )
        AlertDialog(
            onDismissRequest = { viewModel.setShowDialogGender(false) },
            title = { Text(stringResource(id = R.string.gender)) },
            text = {
                Row(Modifier.fillMaxWidth()) {

                    //Text(stringResource(id = R.string.gender_subtitle))

                    for (i in 0..1) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .selectable(
                                    selected = ((i != 0) == selectedOption),
                                    onClick = {
                                        datastoreViewModel.saveGenderToDataStore(i != 0)
                                    }
                                ),
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(
                                    id = viewModel.getImageIdsGender[i]
                                ),
                                contentDescription = radioOptions[i],
                                modifier = Modifier.size(60.dp)
                            )
                            Spacer(modifier = Modifier.size(12.dp))
                            Text(
                                text = radioOptions[i],
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            RadioButton(
                                selected = ((i != 0) == selectedOption),
                                onClick = {
                                    datastoreViewModel.saveGenderToDataStore(i != 0)
                                }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.setShowDialogGender(false)
                }) {
                    Text("ok".uppercase())
                }
            },
        )
    }
}
