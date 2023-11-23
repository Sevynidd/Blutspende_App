package de.agb.blutspende_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import de.agb.blutspende_app.R
import de.agb.blutspende_app.model.ISupplyItem
import de.agb.blutspende_app.ui.theme.BlooddonationAppTheme
import de.agb.blutspende_app.viewmodel.GlobalFunctions
import de.agb.blutspende_app.viewmodel.VMDatastore
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Supply() {
    BlooddonationAppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
            ) {
                val dataStore: VMDatastore = viewModel()
                val globalFunctions: GlobalFunctions = viewModel()

                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                    val (containerSupply) = createRefs()

                    val cardPadding = 10.dp

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(cardPadding)
                            .constrainAs(containerSupply) {
                                top.linkTo(parent.top, margin = 20.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        // TITLE
                        Text(
                            text = stringResource(id = R.string.supplyTitle),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(cardPadding),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        // SUBTITLE
                        Text(
                            text = stringResource(id = R.string.supplySubtitle),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(cardPadding),
                            textAlign = TextAlign.Center
                        )

                        Divider(
                            color = MaterialTheme.colorScheme.background,
                            modifier = Modifier.padding(cardPadding)
                        )

                        // DATE
                        val dateFormatter =
                            DateTimeFormatter.ofPattern(stringResource(id = R.string.dateFormat))
                        Text(
                            text = stringResource(
                                id = R.string.statusWithDate,
                                LocalDate.now().format(dateFormatter)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(cardPadding),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                        // DATE TEXT
                        Text(
                            text = stringResource(
                                id = R.string.yourBloodgroupAvailable,
                                globalFunctions.getBloodgroupAsString(
                                    dataStore.getBloodgroup.collectAsState(
                                        0
                                    ).value
                                ),
                                when (dataStore.getRhesus.collectAsState(true).value) {
                                    true -> "pos"
                                    false -> "neg"
                                },
                                stringResource(id = R.string.moderately)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(cardPadding),
                            textAlign = TextAlign.Center
                        )

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            for (i in 0..3) {
                                DefinitionSupplyItems(
                                    object : ISupplyItem {
                                        override val height: Dp
                                            get() = 40.dp
                                        override val icon: Int
                                            get() = when (i) {
                                                0 -> R.drawable.blood_0
                                                1 -> R.drawable.blood_0
                                                2 -> R.drawable.blood_a
                                                3 -> R.drawable.blood_a
                                                else -> R.drawable.blood_bag_default
                                            }
                                        override val name: String
                                            get() = when (i % 2) {
                                                0 -> "pos"
                                                1 -> "neg"
                                                else -> {
                                                    "pos"
                                                }
                                            }

                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            for (i in 0..3) {
                                DefinitionSupplyItems(
                                    object : ISupplyItem {
                                        override val height: Dp
                                            get() = 40.dp
                                        override val icon: Int
                                            get() = when (i) {
                                                0 -> R.drawable.blood_b
                                                1 -> R.drawable.blood_b
                                                2 -> R.drawable.blood_ab
                                                3 -> R.drawable.blood_ab
                                                else -> R.drawable.blood_bag_default
                                            }
                                        override val name: String
                                            get() = when (i % 2) {
                                                0 -> "pos"
                                                1 -> "neg"
                                                else -> {
                                                    "pos"
                                                }
                                            }

                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.size(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DefinitionSupplyItems(item: ISupplyItem, modifier: Modifier) {
    val dataStore: VMDatastore = viewModel()
    val globalFunctions: GlobalFunctions = viewModel()

    val nameRhesus = when (dataStore.getRhesus.collectAsState(true).value) {
        true -> "pos"
        false -> "neg"
    }

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.name,
            tint = if ((nameRhesus == item.name) and (item.icon == globalFunctions.getBloodbagIconFromBloodgroupID(
                    dataStore.getBloodgroup.collectAsState(0).value
                ))
            ) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
        Text(text = item.name, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }

}