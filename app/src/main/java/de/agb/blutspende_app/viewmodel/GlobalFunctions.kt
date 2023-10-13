package de.agb.blutspende_app.viewmodel

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.ViewModel
import de.agb.blutspende_app.model.ScreenDefinition

class GlobalFunctions : ViewModel() {

    /**
     * @param fullText Der komplette Text, welcher hinzugefügt werden soll
     * @param linkText Die Textabschnitte, welche einen Hyperlink bekommen sollen
     * @param hyperlinks Die Hyperlinks, welche hinzugefügt werden sollen
     * @param style Der Style, wie der Hyperlink aussehen soll
     */
    @Composable
    fun AddHyperlinkToText(
        fullText: String,
        linkText: List<String>,
        hyperlinks: List<String>,
        style: SpanStyle
    ) {

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
                    style = style,
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

    val getScreenRouteDashboard: String
        get() = ScreenDefinition.Dashboard.route

    val getScreenRouteBlutwerte: String
        get() = ScreenDefinition.Blutwerte.route

    val getScreenRouteVorrat: String
        get() = ScreenDefinition.Vorrat.route

    val getScreenRouteSettings: String
        get() = ScreenDefinition.Settings.route

    val getScreenRouteSettingsBlutgruppe: String
        get() = ScreenDefinition.SettingsBlutgruppe.route

    /**
     * @param value Id, welche man aus dataStore.getBlutgruppe erhält
     */
    fun getBlutgruppeAsString(value: Int): String {
        return when (value) {
            0 -> "0"
            1 -> "A"
            2 -> "B"
            3 -> "AB"
            else -> ""
        }
    }

    /**
     * @param value Boolean, welchen man aus dataStore.getRhesus erhält
     */
    fun getRhesusAsString(value: Boolean): String {
        return when (value) {
            false -> "Negativ"
            true -> "Positiv"
        }
    }

    /**
     * @param value Boolean, welchen man aus dataStore.getKell erhält
     */
    fun getKellAsString(value: Boolean): String {
        return getRhesusAsString(value)
    }

}