package de.agb.blutspende_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import de.agb.blutspende_app.R
import de.agb.blutspende_app.model.ISettingsItem
import de.agb.blutspende_app.ui.theme.BlooddonationAppTheme
import de.agb.blutspende_app.viewmodel.GlobalFunctions
import de.agb.blutspende_app.viewmodel.VMDatastore
import de.agb.blutspende_app.viewmodel.screens.settings.VMSettings

@Composable
fun Settings(navController: NavHostController) {
    BlooddonationAppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .safeContentPadding()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    Text(
                        text = stringResource(id = R.string.settings_general),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.size(10.dp))

                    ThemeItem()

                    Spacer(Modifier.size(10.dp))

                    SexItem()


                    Spacer(Modifier.size(10.dp))
                    Divider()
                    Spacer(Modifier.size(10.dp))


                    Text(
                        text = stringResource(id = R.string.blood_donation),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.size(10.dp))

                    BloodgroupItem(navController)
                }
            }
        }
    }
}

@Composable
fun DefinitionSettingsItem(item: ISettingsItem) {
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
                style =
                MaterialTheme.typography.labelLarge.merge(MaterialTheme.colorScheme.onBackground)
            )
            item.subTitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium.merge(MaterialTheme.colorScheme.onBackground)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeItem() {
    val vmDatastore: VMDatastore = viewModel()
    val vmSettings: VMSettings = viewModel()

    val selectedOption by vmDatastore.getThemeMode.collectAsState(0)

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    DefinitionSettingsItem(
        object : ISettingsItem {
            override val title = stringResource(id = R.string.theme)
            override val subTitle =
                stringResource(id = R.string.theme_system) + " / " +
                        stringResource(id = R.string.theme_light) + " / " +
                        stringResource(id = R.string.theme_dark)
            override val icon = R.drawable.paint_brush

            override val onClick = {
                isSheetOpen = true
            }
        }
    )

    val sheetState = rememberModalBottomSheetState()

    if (isSheetOpen) {
        val radioOptions = listOf(
            stringResource(id = R.string.theme_system),
            stringResource(id = R.string.theme_light),
            stringResource(id = R.string.theme_dark)
        )

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { isSheetOpen = isSheetOpen.not() }
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
            ) {

                Text(
                    text = stringResource(id = R.string.theme),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.size(20.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (i in 0..2) {
                        Column(Modifier
                            .weight(1f)
                            .selectable(
                                selected = (i == selectedOption),
                                onClick = {
                                    vmDatastore.saveThemeToDataStore(i)
                                }
                            ),
                            horizontalAlignment = CenterHorizontally) {
                            Image(
                                painter = painterResource(
                                    id = vmSettings.getImageIdsTheme[i]
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
                                    vmDatastore.saveThemeToDataStore(i)
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.size(30.dp))
            }
        }
    }
}

@Composable
fun BloodgroupItem(navController: NavHostController) {
    val globalFunctions: GlobalFunctions = viewModel()

    DefinitionSettingsItem(
        object : ISettingsItem {
            override val title = stringResource(id = R.string.bloodgroup)
            override val subTitle =
                "ABO, Rhesus, " + stringResource(id = R.string.rhesuskomplex) + " & Kell"
            override val icon = R.drawable.blood_drop
            override val onClick = {
                navController.navigate(globalFunctions.getScreenRouteSettingsBloodgroup)
            }
            override val rightArrowButtonVisible = true
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SexItem() {
    val viewModel: VMSettings = viewModel()
    val vmDatastore: VMDatastore = viewModel()

    val selectedOption by vmDatastore.getGender.collectAsState(false)

    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    DefinitionSettingsItem(
        object : ISettingsItem {
            override val title = stringResource(id = R.string.gender)
            override val icon = R.drawable.gender
            override val onClick = {
                isSheetOpen = true
            }
        }
    )

    val sheetState = rememberModalBottomSheetState()

    if (isSheetOpen) {
        val radioOptions = listOf(
            stringResource(id = R.string.male),
            stringResource(id = R.string.female)
        )

        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { isSheetOpen = isSheetOpen.not() }
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
            ) {

                Text(
                    text = stringResource(id = R.string.gender),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.size(14.dp))

                Text(
                    text = stringResource(id = R.string.gender_subtitle),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.size(20.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (i in 0..1) {
                        Column(Modifier
                            .weight(1f)
                            .selectable(
                                selected = ((i != 0) == selectedOption),
                                onClick = {
                                    vmDatastore.saveGenderToDataStore(i != 0)
                                }
                            ),
                            horizontalAlignment = CenterHorizontally) {
                            Image(
                                painter = painterResource(
                                    id = viewModel.getImageIdsGender[i]
                                ),
                                contentDescription = radioOptions[i],
                                modifier = Modifier.size(50.dp)
                            )
                            Spacer(modifier = Modifier.size(12.dp))
                            Text(
                                text = radioOptions[i],
                                textAlign = TextAlign.Left
                            )
                            RadioButton(
                                selected = ((i != 0) == selectedOption),
                                onClick = {
                                    vmDatastore.saveGenderToDataStore(i != 0)
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.size(30.dp))
            }
        }
    }
}
