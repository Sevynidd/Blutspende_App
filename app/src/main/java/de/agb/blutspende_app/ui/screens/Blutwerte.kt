package de.agb.blutspende_app.ui.screens

import android.widget.Toast
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
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.agb.blutspende_app.R
import de.agb.blutspende_app.ui.theme.Blutspende_AppTheme

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

                val density = LocalDensity.current
                val context = LocalContext.current

                Column(
                    Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {

                    for (i in 0..2) {
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
                                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(2.dp),
                                modifier = Modifier
                                    .padding(bottom = 15.dp)
                                    .size(40.dp),
                                onClick = {
                                    Toast.makeText(
                                        context,
                                        when (i) {
                                            0 -> "FABPlus"
                                            1 -> "FABMinus"
                                            else -> "FabEdit"
                                        }, Toast.LENGTH_SHORT
                                    ).show()
                                    menuOpen = menuOpen.not()
                                }) {
                                Icon(
                                    painter = painterResource(
                                        id = when (i) {
                                            0 -> R.drawable.plus
                                            1 -> R.drawable.minus
                                            else -> R.drawable.edit
                                        }
                                    ),
                                    contentDescription = when (i) {
                                        0 -> "FABPlus"
                                        1 -> "FABMinus"
                                        else -> "FabEdit"
                                    },
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    FloatingActionButton(shape = MaterialTheme.shapes.medium.copy(
                        CornerSize(
                            percent = 50
                        )
                    ),
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(2.dp),
                        modifier = Modifier
                            .padding(bottom = 30.dp)
                            .size(50.dp),
                        onClick = { menuOpen = menuOpen.not() }) {
                        Crossfade(
                            targetState = currentMenuIcon,
                            label = "CrossfadeMenuIcon"
                        ) { icon ->
                            Icon(
                                painter = painterResource(id = icon),
                                contentDescription = "Menu",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}