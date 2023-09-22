package de.agb.blutspende_app.ui.screens

import de.agb.blutspende_app.R
import de.agb.blutspende_app.ui.theme.Blutspende_AppTheme
import de.agb.blutspende_app.viewmodel.DatastoreViewModel
import de.agb.blutspende_app.viewmodel.settings.BlutgruppeViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

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
                val datastoreViewModel: DatastoreViewModel = viewModel()
                val blutgruppeViewModel: BlutgruppeViewModel = viewModel()

                Blutgruppe(datastoreViewModel, blutgruppeViewModel)

            }
        }
    }
}

@Composable
fun Blutgruppe(datastoreViewModel: DatastoreViewModel, blutgruppeViewModel: BlutgruppeViewModel) {

    val showDialog = blutgruppeViewModel.getShowDialogBlutgruppe

    Row(
        Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "ABO-System", fontSize = 22.sp)

        IconButton(onClick = { blutgruppeViewModel.setShowDialogBlutgruppe(showDialog.not()) }) {
            Icon(Icons.Rounded.Info, contentDescription = "InfoBlutgruppe")
        }

        if (showDialog) {
            val configuration = LocalConfiguration.current
            val screenHeight = configuration.screenHeightDp.dp

            AlertDialog(modifier = Modifier.heightIn(250.dp, screenHeight - 150.dp),
                onDismissRequest = { blutgruppeViewModel.setShowDialogBlutgruppe(false) },
                title = { Text("Was ist das AB0-System?") },
                text = {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        Text(
                            "Alle roten Blutkörperchen (Erythrozyten) sind von einer Hülle umgeben. \n" +
                                    "Auf dieser Hülle befinden sich charakteristische Strukturen (Antigene), die die roten Blutkörperchen eines Menschen von denen eines anderen unterscheiden.\n" +
                                    "Im AB0-Blutgruppensystem werden die Antigene auf der Hülle der roten Blutkörperchen in vier Gruppen unterteilt: A, B, AB und 0 stellen die vier Blutgruppen dar.\n" +
                                    "\n" + "Blutgruppe A: Es befindet sich nur das Antigen A auf der Hülle der roten Blutkörperchen.\n" +
                                    "Blutgruppe B: Es ist nur das Antigen B vorhanden.\n" + "Blutgruppe 0: Es sind keine Antigene vorhanden.\n" +
                                    "Blutgruppe AB: Es befinden sich beide Antigene A und B auf den roten Blutkörperchen."
                        )
                        Image(
                            painter = painterResource(id = R.drawable.unterscheidung_der_blutgruppen),
                            contentDescription = "Blutgruppen"
                        )

                        AddHyperlinkToText(
                            fullText = "Quelle: blutspenden.de",
                            linkText = listOf("blutspenden.de"),
                            hyperlinks = listOf("https://www.blutspenden.de/rund-ums-blut/blutgruppen/")
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        blutgruppeViewModel.setShowDialogBlutgruppe(false)
                    }) {
                        Text("ok".uppercase())
                    }
                })
        }
    }

}

/**
 * @param fullText Der komplette Text, welcher hinzugefügt werden soll
 * @param linkText Die Textabschnitte, welche einen Hyperlink bekommen sollen
 * @param hyperlinks Die Hyperlinks, welche hinzugefügt werden sollen
 */
@Composable
fun AddHyperlinkToText(fullText: String, linkText: List<String>, hyperlinks: List<String>) {

    val annotatedText = buildAnnotatedString {
        append(fullText)
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            start = 0,
            end = fullText.length
        )

        linkText.forEachIndexed { index, link ->
            val startIndex = fullText.indexOf(link)
            val endIndex = startIndex + link.length

            addStyle(
                style = SpanStyle(
                    color = colorResource(id = R.color.blue),
                    textDecoration = TextDecoration.Underline
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = hyperlinks[index],
                start = startIndex,
                end = endIndex
            )
        }
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        text = annotatedText,
        onClick = {
            annotatedText.getStringAnnotations("URL", start = it, end = it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}