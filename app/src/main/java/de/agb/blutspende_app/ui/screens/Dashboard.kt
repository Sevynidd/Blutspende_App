package de.agb.blutspende_app.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import de.agb.blutspende_app.R
import de.agb.blutspende_app.model.roomDatabase.BloodValues
import de.agb.blutspende_app.model.roomDatabase.BloodValuesState
import de.agb.blutspende_app.ui.theme.BlooddonationAppTheme
import de.agb.blutspende_app.viewmodel.GlobalFunctions
import de.agb.blutspende_app.viewmodel.VMDatastore

@Composable
fun Home(state: BloodValuesState) {
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
                    val (textBlooddonation, userImage, bloodBag, containerBlooddonationDetails) = createRefs()

                    val gender = dataStore.getGender.collectAsState(false)
                    var newestDonation = BloodValues(
                        0,
                        0,
                        0,
                        0f,
                        0,
                        0L,
                        0,
                        0
                    )

                    state.bloodValuesList.forEachIndexed { index, bloodValues ->
                        if (state.bloodValuesList[index].fTypID == 0) {
                            newestDonation = bloodValues
                        }
                    }

                    // Period in milliseconds -> https://www.blutspendedienst-owl.de/blutspende/vollblutspende.html
                    // women -> 13 weeks
                    // men -> 9 weeks
                    val donationPeriod = when (gender.value) {
                        true -> 7862400000L
                        false -> 5443200000L
                    }

                    Text(
                        text = if ((System.currentTimeMillis() - donationPeriod) > newestDonation.timestamp) {
                            stringResource(id = R.string.youCanDonate)
                        } else {
                            "Du darfst erst wieder am " + (globalFunctions.dateFormat.format(
                                newestDonation.timestamp + donationPeriod
                            )) + " Vollblut spenden!"
                        },
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .constrainAs(textBlooddonation) {
                                top.linkTo(parent.top, margin = 80.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    Image(painterResource(
                        id =
                        when (dataStore.getGender.collectAsState(false).value) {
                            true -> R.drawable.woman1
                            false -> R.drawable.man2
                        }

                    ), contentDescription = "user",
                        modifier = Modifier
                            .size(180.dp)
                            .background(MaterialTheme.colorScheme.onBackground, CircleShape)
                            .constrainAs(userImage) {
                                top.linkTo(textBlooddonation.bottom, margin = 60.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            })

                    Icon(painterResource(
                        id =
                        globalFunctions.getBloodbagIconFromBloodgroupID(
                            dataStore.getBloodgroup.collectAsState(
                                0
                            ).value
                        )
                    ),
                        contentDescription = "bloodBag",
                        tint = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                MaterialTheme.colorScheme.onBackground,
                                CircleShape
                            )
                            .constrainAs(bloodBag) {
                                start.linkTo(userImage.end, margin = (-60).dp)
                                bottom.linkTo(userImage.top, margin = (-70).dp)
                            })

                    val cardPadding = 10.dp
                    Card(
                        Modifier
                            .fillMaxWidth(0.8f)
                            .padding(cardPadding)
                            .constrainAs(containerBlooddonationDetails) {
                                top.linkTo(userImage.bottom, margin = 60.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }) {

                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(cardPadding)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = stringResource(id = R.string.bloodgroup),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = globalFunctions.getBloodgroupAsString(
                                            dataStore.getBloodgroup.collectAsState(0).value
                                        )
                                    )

                                }

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "Rhesus", fontWeight = FontWeight.SemiBold)
                                    Text(
                                        text = globalFunctions.getRhesusAsString(
                                            dataStore.getRhesus.collectAsState(true).value
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.size(6.dp))

                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(cardPadding)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                    Text(
                                        text = stringResource(id = R.string.rhesuskomplex),
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = dataStore.getRhesuscomplex.collectAsState("").value
                                    )

                                }

                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "Kell", fontWeight = FontWeight.SemiBold)
                                    Text(
                                        text = globalFunctions.getKellAsString(
                                            dataStore.getKell.collectAsState(true).value
                                        )
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}