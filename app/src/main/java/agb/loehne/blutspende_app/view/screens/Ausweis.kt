package agb.loehne.blutspende_app.view.screens

import agb.loehne.blutspende_app.view.theme.Blutspende_AppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Ausweis() {
    Blutspende_AppTheme {
        Surface {
            Column(modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)) {
                Text("Ausweis")
            }
        }
    }
}