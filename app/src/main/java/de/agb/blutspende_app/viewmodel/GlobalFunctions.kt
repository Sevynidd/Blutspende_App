package de.agb.blutspende_app.viewmodel

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.ViewModel
import de.agb.blutspende_app.R
import de.agb.blutspende_app.model.ScreenDefinition
import java.text.DateFormat
import java.text.DateFormat.MEDIUM
import java.text.DateFormat.getDateInstance


class GlobalFunctions : ViewModel() {

    val dateFormat: DateFormat
        get() = getDateInstance(MEDIUM)

    /**
     * @param url URL, from which the BaseURL should be taken
     * Ex.: https://www.youtube.com/watch?v=dQw4w9WgXcQ -> www.youtube.com
     */
    fun getBaseURL(url: String): String {
        val urlSplitted = url.split("/", limit = 4)

        return urlSplitted[2]
    }

    /**
     * @param fullText The whole text
     * @param linkText Textsections, which should get links
     * @param hyperlinks The hyperlinks, which the linkText should get
     * @param style Style for the hyperlink
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
                    color = MaterialTheme.colorScheme.onPrimaryContainer
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
                    tag = "URL", annotation = hyperlinks[index], start = startIndex, end = endIndex
                )
            }
        }

        val uriHandler = LocalUriHandler.current

        ClickableText(text = annotatedText, onClick = {
            annotatedText.getStringAnnotations("URL", start = it, end = it).firstOrNull()
                ?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        })
    }

    val getScreenRouteDashboard: String
        get() = ScreenDefinition.Dashboard.route

    val getScreenRouteBloodvalue: String
        get() = ScreenDefinition.BloodValues.route

    val getScreenRouteSupply: String
        get() = ScreenDefinition.Supply.route

    val getScreenRouteSettings: String
        get() = ScreenDefinition.Settings.route

    val getScreenRouteSettingsBloodgroup: String
        get() = ScreenDefinition.SettingsBloodgroup.route

    val getScreenRouteSettingsBloodgroupWebview: String
        get() = ScreenDefinition.SettingsBloodgroupWebview.route

    /**
     * @param value Id, which you can get from dataStore.getBloodgroup
     */
    fun getBloodgroupAsString(value: Int): String {
        return when (value) {
            0 -> "0"
            1 -> "A"
            2 -> "B"
            3 -> "AB"
            else -> ""
        }
    }

    /**
     * @param value Boolean, which you can get from dataStore.getRhesus
     */
    fun getRhesusAsString(value: Boolean): String {
        return when (value) {
            false -> "Negativ"
            true -> "Positiv"
        }
    }

    /**
     * @param value Boolean, which you can get from dataStore.getKell
     */
    fun getKellAsString(value: Boolean): String {
        return getRhesusAsString(value)
    }

    /**
     * @param id Id, which you can get from dataStore.getBloodgroup
     */
    fun getBloodbagIconFromBloodgroupID(id: Int): Int {
        return when (id) {
            0 -> R.drawable.blood_0
            1 -> R.drawable.blood_a
            2 -> R.drawable.blood_b
            3 -> R.drawable.blood_ab
            else -> R.drawable.blood_bag_default
        }
    }
}