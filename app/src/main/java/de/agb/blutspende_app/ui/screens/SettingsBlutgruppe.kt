package de.agb.blutspende_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.agb.blutspende_app.R
import de.agb.blutspende_app.ui.theme.Blutspende_AppTheme
import de.agb.blutspende_app.viewmodel.DatastoreViewModel
import de.agb.blutspende_app.viewmodel.GlobalFunctions
import de.agb.blutspende_app.viewmodel.screens.settings.SettingsBlutgruppeViewModel

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
    val datastoreViewModel: DatastoreViewModel = viewModel()
    val viewModel: SettingsBlutgruppeViewModel = viewModel()

    val showDialog = viewModel.getShowDialog

    Row(
        Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "ABO-System", fontSize = 22.sp)

        IconButton(onClick = { viewModel.setShowDialog(showDialog.not()) }) {
            Icon(Icons.Rounded.Info, contentDescription = "InfoBlutgruppe")
        }

        if (showDialog) {
            val configuration = LocalConfiguration.current
            val screenHeight = configuration.screenHeightDp.dp

            AlertDialog(modifier = Modifier.heightIn(250.dp, screenHeight - 150.dp),
                onDismissRequest = { viewModel.setShowDialog(false) },
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
                        viewModel.setShowDialog(false)
                    }) {
                        Text("ok".uppercase())
                    }
                })
        }
    }

}