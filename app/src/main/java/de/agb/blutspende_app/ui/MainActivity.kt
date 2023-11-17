package de.agb.blutspende_app.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.commandiron.bubble_navigation_bar_compose.BubbleNavigationBar
import com.commandiron.bubble_navigation_bar_compose.BubbleNavigationBarItem
import de.agb.blutspende_app.R
import de.agb.blutspende_app.model.roomDatabase.Arm
import de.agb.blutspende_app.model.roomDatabase.BloodValuesDatabase
import de.agb.blutspende_app.model.roomDatabase.BloodValuesEvent
import de.agb.blutspende_app.model.roomDatabase.BloodValuesState
import de.agb.blutspende_app.model.roomDatabase.Type
import de.agb.blutspende_app.ui.navigation.SetupNavbarGraph
import de.agb.blutspende_app.ui.theme.Blooddonation_AppTheme
import de.agb.blutspende_app.viewmodel.GlobalFunctions
import de.agb.blutspende_app.viewmodel.VMMainActivity
import de.agb.blutspende_app.viewmodel.screens.database.VMBloodValues
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val vmDatabase by viewModels<VMBloodValues>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return VMBloodValues(
                        BloodValuesDatabase.getInstance(applicationContext).bloodValuesDao()
                    ) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Setzen eines Splashscreen
        installSplashScreen()
        //Fullscreen App
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Blooddonation_AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val databaseState by vmDatabase.state.collectAsState()

                    //DatabaseInit(this.applicationContext)

                    NavigationBar(databaseState, vmDatabase::onEvent)
                }
            }
        }
    }
}

@Composable
fun DatabaseInit(context: Context) {

    val dao = BloodValuesDatabase.getInstance(context).bloodValuesDao()
    val arm = listOf(
        Arm(armID = 0, bezeichnung = "Linker Arm"),
        Arm(armID = 1, bezeichnung = "Rechter Arm")
    )
    val typ = listOf(
        Type(typID = 0, blutspendeTyp = "Vollblut"),
        Type(typID = 1, blutspendeTyp = "Plasma"),
        Type(typID = 2, blutspendeTyp = "Thrombozyten")
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = true) {
        lifecycleOwner.lifecycleScope.launch {
            arm.forEach { dao.addArm(it) }
            typ.forEach { dao.addType(it) }
        }
    }
}

@Composable
fun NavigationBar(state: BloodValuesState, onEvent: (BloodValuesEvent) -> Unit) {

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        contentWindowInsets = WindowInsets(30.dp, 20.dp, 30.dp, 0.dp),
        topBar = {
            TopBar(navController)
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                SetupNavbarGraph(navController = navController, state, onEvent)
            }
        },
        bottomBar = {
            BottomBar(navController)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {

    val currentRoute = currentRoute(navController = navController)
    val globalFunctions: GlobalFunctions = viewModel()

    TopAppBar(
        title = {
            Text(
                text =
                when (currentRoute) {
                    globalFunctions.getScreenRouteDashboard -> stringResource(id = R.string.screenDashboard)
                    globalFunctions.getScreenRouteBloodvalue -> stringResource(id = R.string.screenBloodLevels)
                    globalFunctions.getScreenRouteSupply -> stringResource(id = R.string.screenSupply)
                    else -> stringResource(id = R.string.settings)
                },
                fontSize = 20.sp
            )
        },
        navigationIcon = {
            if (currentRoute == globalFunctions.getScreenRouteSettingsBloodgroup) {
                IconButton(
                    onClick = {
                        navController.navigate(globalFunctions.getScreenRouteSettings)
                    },
                    enabled = true
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        "Zurück"
                    )
                }
            } else if (currentRoute == globalFunctions.getScreenRouteSettingsBloodgroupWebview) {
                IconButton(
                    onClick = {
                        navController.navigate(globalFunctions.getScreenRouteSettingsBloodgroup)
                    },
                    enabled = true
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        "Zurück"
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {
                if (currentRoute != globalFunctions.getScreenRouteSettings) {
                    navController.navigate(globalFunctions.getScreenRouteSettings)
                }
            }, modifier = Modifier.size(35.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = "Einstellungen"
                )
            }
        }
    )
}

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val viewModel: VMMainActivity = viewModel()
    val globalFunctions: GlobalFunctions = viewModel()

    BubbleNavigationBar {
        val currentRoute = currentRoute(navController = navController)
        viewModel.getNavigationItems.forEach { item ->
            BubbleNavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                },
                icon = item.iconId,
                title =
                when (item.route) {
                    globalFunctions.getScreenRouteDashboard -> stringResource(id = R.string.screenDashboard)
                    globalFunctions.getScreenRouteBloodvalue -> stringResource(id = R.string.screenBloodLevels)
                    globalFunctions.getScreenRouteSupply -> stringResource(id = R.string.screenSupply)
                    else -> stringResource(id = R.string.settings)
                },
                selectedColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unSelectedIconColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}