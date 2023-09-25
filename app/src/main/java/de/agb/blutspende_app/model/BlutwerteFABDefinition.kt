package de.agb.blutspende_app.model

import de.agb.blutspende_app.R
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class BlutwerteFABDefinition(
    val bottompadding: Dp,
    val iconSize: Dp,
    val size: Dp,
    val iconId: Int,
    val contentDescription: String
) {
    data object Plus : BlutwerteFABDefinition(
        bottompadding = 15.dp,
        iconSize = 18.dp,
        size = 40.dp,
        iconId = R.drawable.plus,
        contentDescription = "PlusFAB"
    )

    data object Minus : BlutwerteFABDefinition(
        bottompadding = 15.dp,
        iconSize = 18.dp,
        size = 40.dp,
        iconId = R.drawable.minus,
        contentDescription = "MinusFAB"
    )

    data object Edit : BlutwerteFABDefinition(
        bottompadding = 15.dp,
        iconSize = 18.dp,
        size = 40.dp,
        iconId = R.drawable.edit,
        contentDescription = "EditFAB"
    )


}