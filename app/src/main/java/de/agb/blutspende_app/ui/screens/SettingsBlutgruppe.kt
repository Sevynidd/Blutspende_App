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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.agb.blutspende_app.R
import de.agb.blutspende_app.ui.theme.Blutspende_AppTheme
import de.agb.blutspende_app.viewmodel.VMDatastore
import de.agb.blutspende_app.viewmodel.screens.settings.VMSettingsBlutgruppe

@Composable
fun SettingsBlutgruppe() {
    Blutspende_AppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
            ) {
                Blutgruppe()
            }
        }
    }
}

@Composable
fun Blutgruppe() {
    val viewModel: VMSettingsBlutgruppe = viewModel()
    val vmDatastore: VMDatastore = viewModel()

    val showAB0SystemHint = viewModel.getIsVisibleAB0System

    Column(
        Modifier.fillMaxWidth()
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "ABO-System", fontSize = 22.sp)

            IconButton(onClick = { viewModel.setIsVisibleAB0System(showAB0SystemHint.not()) }) {
                Icon(Icons.Rounded.Info, contentDescription = "InfoBlutgruppe")
            }
        }

        AnimatedVisibility(showAB0SystemHint) {
            Card(
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.clickable {
                    viewModel.setIsVisibleAB0System(showAB0SystemHint.not())
                }
            ) {
                Text(
                    text = stringResource(id = R.string.AB0System_Help_Title),
                    modifier = Modifier.padding(12.dp)
                )
                Text(
                    text = stringResource(id = R.string.AB0System_Help_Text1),
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        if (showAB0SystemHint) {
            Spacer(modifier = Modifier.size(16.dp))
        }

        Column(modifier = Modifier.fillMaxWidth()) {

            val selectedOption by vmDatastore.getBlutgruppe.collectAsState(0)
            val images = listOf(
                R.drawable.blood_0,
                R.drawable.blood_a,
                R.drawable.blood_b,
                R.drawable.blood_ab
            )
            val text = listOf(
                "0",
                "A",
                "B",
                "AB"
            )

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
                                    id = images[i]
                                ),
                                contentDescription = text[i]
                            )
                            Text(
                                text = text[i],
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

    /*
    if (showDialog) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp

        AlertDialog(modifier = Modifier.heightIn(250.dp, screenHeight - 150.dp),
            onDismissRequest = { viewModel.setIsVisibleAB0System(false) },
            title = { Text(stringResource(id = R.string.AB0System_Help_Title)) },
            text = {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Text(stringResource(id = R.string.AB0System_Help_Text))
                    Image(
                        painter = painterResource(id = R.drawable.unterscheidung_der_blutgruppen),
                        contentDescription = "Blutgruppen"
                    )

                    val globalFunctions: GlobalFunctions = viewModel()

                    globalFunctions.AddHyperlinkToText(
                        fullText = stringResource(id = R.string.source) + ": blutspenden.de",
                        linkText = listOf("blutspenden.de"),
                        hyperlinks = listOf("https://www.blutspenden.de/rund-ums-blut/blutgruppen/"),
                        style = SpanStyle(
                            color = colorResource(id = R.color.blue),
                            textDecoration = TextDecoration.Underline
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.setIsVisibleAB0System(false)
                }) {
                    Text("ok".uppercase())
                }
            })
    }
     */

}