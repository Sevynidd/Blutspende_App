package de.agb.blutspende_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import de.agb.blutspende_app.R
import de.agb.blutspende_app.ui.theme.Blutspende_AppTheme
import de.agb.blutspende_app.viewmodel.DatastoreViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Vorrat() {
    Blutspende_AppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                val dataStore: DatastoreViewModel = viewModel()

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
                        Text(
                            text = stringResource(id = R.string.supplyTitle),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(cardPadding),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
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
                        Text(
                            text = stringResource(
                                id = R.string.yourBloodgroupAvailable,
                                dataStore.getBlutgruppe.collectAsState("").value,
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

                    }
                }
            }
        }
    }
}