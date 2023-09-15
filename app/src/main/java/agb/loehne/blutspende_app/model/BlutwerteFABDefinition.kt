package agb.loehne.blutspende_app.model

import agb.loehne.blutspende_app.R
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
        bottompadding = 20.dp,
        iconSize = 20.dp,
        size = 46.dp,
        iconId = R.drawable.plus,
        contentDescription = "PlusFAB"
    )

    data object Minus : BlutwerteFABDefinition(
        bottompadding = 20.dp,
        iconSize = 20.dp,
        size = 46.dp,
        iconId = R.drawable.minus,
        contentDescription = "MinusFAB"
    )

    data object Edit : BlutwerteFABDefinition(
        bottompadding = 20.dp,
        iconSize = 20.dp,
        size = 46.dp,
        iconId = R.drawable.edit,
        contentDescription = "EditFAB"
    )


}