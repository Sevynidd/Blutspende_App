package agb.loehne.blutspende_app.ui.screens

import agb.loehne.blutspende_app.R
import agb.loehne.blutspende_app.model.BlutwerteFABDefinition
import agb.loehne.blutspende_app.ui.theme.Blutspende_AppTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun Blutwerte() {
    Blutspende_AppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {

                var menuOpen by remember {
                    mutableStateOf(false)
                }
                val currentMenuIcon: Int by rememberUpdatedState(
                    if (menuOpen) R.drawable.close else R.drawable.menu
                )
                val fabs = listOf(
                    BlutwerteFABDefinition.Edit,
                    BlutwerteFABDefinition.Minus,
                    BlutwerteFABDefinition.Plus
                )
                val density = LocalDensity.current

                Column(
                    Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {

                    fabs.forEach { fab ->
                        AnimatedVisibility(menuOpen,
                            enter = slideInVertically {
                                with(density) {
                                    60.dp.roundToPx()
                                }
                            }) {
                            FloatingActionButton(shape = MaterialTheme.shapes.medium.copy(
                                CornerSize(
                                    percent = 50
                                )
                            ),
                                modifier = Modifier
                                    .padding(bottom = fab.bottompadding)
                                    .size(fab.size),
                                onClick = { /*TODO*/ }) {
                                Icon(
                                    painter = painterResource(id = fab.iconId),
                                    contentDescription = fab.contentDescription,
                                    modifier = Modifier.size(fab.iconSize)
                                )
                            }
                        }

                    }

                    FloatingActionButton(shape = MaterialTheme.shapes.medium.copy(
                        CornerSize(
                            percent = 50
                        )
                    ),
                        modifier = Modifier
                            .padding(bottom = 30.dp)
                            .size(56.dp),
                        onClick = { menuOpen = menuOpen.not() }) {
                        Crossfade(
                            targetState = currentMenuIcon,
                            label = "CrossfadeMenuIcon"
                        ) { icon ->
                            Icon(
                                painter = painterResource(id = icon),
                                contentDescription = "Menu",
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }

                    /*
                    if (menuOpen) {

                        FloatingActionButton(shape = MaterialTheme.shapes.medium.copy(
                            CornerSize(
                                percent = 50
                            )
                        ),
                            modifier = Modifier
                                .padding(bottom = fabPositionBottom[3])
                                .size(46.dp),
                            onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit),
                                contentDescription = "Edit",
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        FloatingActionButton(shape = MaterialTheme.shapes.medium.copy(
                            CornerSize(
                                percent = 50
                            )
                        ),
                            modifier = Modifier
                                .padding(bottom = fabPositionBottom[2])
                                .size(46.dp),
                            onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.minus),
                                contentDescription = "Minus",
                                modifier = Modifier.size(20.dp)
                            )
                        }


                        FloatingActionButton(shape = MaterialTheme.shapes.medium.copy(
                            CornerSize(
                                percent = 50
                            )
                        ),
                            modifier = Modifier
                                .padding(bottom = fabPositionBottom[1])
                                .size(46.dp),
                            onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.plus),
                                contentDescription = "Plus",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    FloatingActionButton(
                        shape = MaterialTheme.shapes.medium.copy(CornerSize(percent = 50)),
                        modifier = Modifier
                            .padding(bottom = fabPositionBottom[0]),
                        onClick = { menuOpen = !menuOpen }
                    ) {
                        Crossfade(
                            targetState = currentMenuIcon,
                            label = "CrossfadeMenuIcon"
                        ) { icon ->
                            Icon(
                                painter = painterResource(id = icon),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                }*/
                }

            }
        }
    }
}