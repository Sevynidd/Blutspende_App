package agb.loehne.blutspende_app.ui

import agb.loehne.blutspende_app.R
import agb.loehne.blutspende_app.repository.ScreenDefinition
import agb.loehne.blutspende_app.ui.navigation.SetupNavbarGraph
import agb.loehne.blutspende_app.ui.theme.Blutspende_AppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.commandiron.bubble_navigation_bar_compose.BubbleNavigationBar
import com.commandiron.bubble_navigation_bar_compose.BubbleNavigationBarItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Setzen eines Splashscreen
        installSplashScreen()
        //Fullscreen App
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            Blutspende_AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationBar()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBar() {

    val navController = rememberNavController()

    val navigationItems = listOf(
        ScreenDefinition.Dashboard,
        ScreenDefinition.Ausweis,
        ScreenDefinition.Blutwerte,
        ScreenDefinition.Vorrat
    )

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
                SetupNavbarGraph(navController = navController)
            }
        },
        bottomBar = {
            BottomBar(navController, navigationItems)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController) {
    val currentRoute = currentRoute(navController = navController)

    TopAppBar(
        title = {
            Text(
                when (currentRoute) {
                    ScreenDefinition.Dashboard.route -> ScreenDefinition.Dashboard.name
                    ScreenDefinition.Ausweis.route -> ScreenDefinition.Ausweis.name
                    ScreenDefinition.Blutwerte.route -> ScreenDefinition.Blutwerte.name
                    ScreenDefinition.Vorrat.route -> ScreenDefinition.Vorrat.name
                    ScreenDefinition.Settings.route -> ScreenDefinition.Settings.name
                    else -> ""
                }
            )
        },
        navigationIcon = {
            if (currentRoute == ScreenDefinition.SettingsBlutgruppe.route) {
                IconButton(
                    onClick = {
                        navController.navigate(ScreenDefinition.Settings.route)
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
                if (currentRoute != ScreenDefinition.Settings.route) {
                    navController.navigate(ScreenDefinition.Settings.route)
                }
            }) {
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
    navController: NavHostController,
    items: List<ScreenDefinition>
) {
    BubbleNavigationBar {
        val currentRoute = currentRoute(navController = navController)
        items.forEach { navigationItem ->
            BubbleNavigationBarItem(
                selected = currentRoute == navigationItem.route,
                onClick = {
                    if (currentRoute != navigationItem.route) {
                        navController.navigate(navigationItem.route)
                    }
                },
                icon = navigationItem.iconId,
                title = navigationItem.name,
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