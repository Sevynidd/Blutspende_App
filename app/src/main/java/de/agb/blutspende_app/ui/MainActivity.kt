package de.agb.blutspende_app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.commandiron.bubble_navigation_bar_compose.BubbleNavigationBar
import com.commandiron.bubble_navigation_bar_compose.BubbleNavigationBarItem
import de.agb.blutspende_app.R
import de.agb.blutspende_app.model.ScreenDefinition
import de.agb.blutspende_app.ui.navigation.SetupNavbarGraph
import de.agb.blutspende_app.ui.theme.Blutspende_AppTheme
import de.agb.blutspende_app.viewmodel.MainActivityViewModel

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

@Composable
fun NavigationBar() {

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
                SetupNavbarGraph(navController = navController)
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

    val viewModel: MainActivityViewModel = viewModel()

    TopAppBar(
        title = {
            Text(
                text =
                viewModel.topAppBarTitle(currentRoute),
                fontSize = 20.sp
            )
        },
        navigationIcon = {
            if (currentRoute == viewModel.getSettingsBlutgruppeRoute) {
                IconButton(
                    onClick = {
                        navController.navigate(viewModel.getSettingsRoute)
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
                if (currentRoute != viewModel.getSettingsRoute) {
                    navController.navigate(viewModel.getSettingsRoute)
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
    val viewModel: MainActivityViewModel = viewModel()

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
                title = item.route,
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