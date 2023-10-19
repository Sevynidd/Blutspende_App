package de.agb.blutspende_app.ui.screens

import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import de.agb.blutspende_app.R
import de.agb.blutspende_app.ui.theme.Blutspende_AppTheme
import de.agb.blutspende_app.viewmodel.GlobalFunctions
import de.agb.blutspende_app.viewmodel.VMDatastore

@Composable
fun SettingsBlutgruppeWebview() {
    Blutspende_AppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
            ) {
                val vmDatastore: VMDatastore = viewModel()
                val globalFunctions: GlobalFunctions = viewModel()

                val getHilfeURL =
                    vmDatastore.getBlutspendeInfoURL.collectAsState(initial = "").value

                if (getHilfeURL == "") {
                    Text("Fehler: Es kann keine Website angezeigt werden")
                } else {

                    Column {
                        globalFunctions.AddHyperlinkToText(
                            fullText = "Quelle: blutspende.de",
                            linkText = listOf("blutspende.de"),
                            hyperlinks = listOf(getHilfeURL),
                            style = SpanStyle(
                                color = colorResource(id = R.color.blue)
                            )
                        )
                    }
                    Column {
                        AndroidView(
                            factory = {
                                WebView(it).apply {
                                    layoutParams = ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                    )
                                    setLayerType(View.LAYER_TYPE_HARDWARE, null)
                                    settings.allowFileAccess = true
                                    webViewClient = WebViewClient()
                                    loadUrl(getHilfeURL)
                                }
                            }, update = {
                                it.loadUrl(getHilfeURL)
                            },
                            modifier = Modifier.clip(RoundedCornerShape(10.dp))
                        )
                    }

                    Spacer(modifier = Modifier.size(12.dp))

                }
            }
        }
    }
}