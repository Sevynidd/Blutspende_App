package agb.loehne.blutspende_app.ui.screens

import agb.loehne.blutspende_app.R
import agb.loehne.blutspende_app.repository.ScreenDefinition
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
                    val datastoreViewModel: DatastoreViewModel = viewModel()

                    DialogDarstellung(datastoreViewModel)

                    Spacer(modifier = Modifier.size(20.dp))

                    ButtonBlutgruppe(navController)
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
                    .size(50.dp)
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

@Composable
fun ButtonBlutgruppe(navController: NavHostController) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(ScreenDefinition.SettingsBlutgruppe.route)
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.wrapContentWidth()) {
            Icon(
                painter = painterResource(id = R.drawable.blood_drop),
                "BlutgruppeIcon",
                modifier = Modifier
                    .size(56.dp)
                    .padding(end = 20.dp)
            )
        }

        Column(Modifier.wrapContentWidth()) {
            //Titel
            Text(
                text = "Blutgruppe",
                style = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 15.sp)
            )
            //Untertitel
            Text(
                text = "AB0, Rhesus, Rhesuskomplex & Kell",
                style = TextStyle(color = MaterialTheme.colorScheme.onBackground, fontSize = 12.sp)
            )
        }

        Column(Modifier.weight(1f), horizontalAlignment = Alignment.End) {
            Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "ArrowRight")
        }
    }
}
