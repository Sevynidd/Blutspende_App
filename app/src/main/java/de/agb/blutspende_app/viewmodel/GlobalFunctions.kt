package de.agb.blutspende_app.viewmodel

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.ViewModel
import de.agb.blutspende_app.R

class GlobalFunctions: ViewModel() {

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

}