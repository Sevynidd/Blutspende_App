package agb.loehne.blutspende_app.screens

import agb.loehne.blutspende_app.datastore.StoreSettingsViewModel
import agb.loehne.blutspende_app.ui.theme.Blutspende_AppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Einstellungen() {
    Blutspende_AppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .safeDrawingPadding()
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    //ExposedDropdownMenuBoxDarkMode()
                    DarstellungDialog()
                }
            }
        }
    }
}

@Composable
fun DarstellungDialog() {
    var showDialog by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Default.Edit,
            contentDescription = "DarstellungIcon",
            Modifier.padding(end = 20.dp)
        )
        ClickableText(
            text = AnnotatedString("Darstellung"),
            style = TextStyle(MaterialTheme.colorScheme.primary),
            onClick = {
                showDialog = showDialog.not()
            })

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Are you sure you want to delete this?") },
                text = { Text("This action cannot be undone") },
                confirmButton = {
                    TextButton(onClick = { /* TODO */ }) {
                        Text("Delete it".uppercase())
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel".uppercase())
                    }
                },
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxDarkMode() {
    val items = arrayOf("System", "Hell", "Dunkel")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(items[0]) }
    val viewModel: StoreSettingsViewModel = viewModel()

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Darstellung: ")

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            viewModel.saveToDataStore(items.indexOf(item))
                        }
                    )
                }
            }
        }
    }
}