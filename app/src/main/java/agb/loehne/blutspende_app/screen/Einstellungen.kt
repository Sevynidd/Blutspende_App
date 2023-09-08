package agb.loehne.blutspende_app.screen

import agb.loehne.blutspende_app.ui.theme.Blutspende_AppTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Einstellungen() {
    Blutspende_AppTheme {
        Surface {
            Column(modifier = Modifier.fillMaxSize()) {
                Text("Settings")
            }
        }
    }
}