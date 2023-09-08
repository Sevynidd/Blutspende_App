package agb.loehne.blutspende_app

import agb.loehne.blutspende_app.ui.theme.Blutspende_AppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
                    NavigationBarBottom()
                }
            }
        }
    }
}

@Composable
fun NavigationBarBottom() {
    Scaffold(
        bottomBar = {
            BottomBar()
        },
        contentWindowInsets = WindowInsets(20.dp, 30.dp, 20.dp, 0.dp)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text("Content")
        }
    }
}

@Composable
fun BottomBar() {
    val navController = rememberNavController()
    SetupNavGraph(navController = navController)

    val navigationItems = listOf(
        ScreenDefinition.Home,
        ScreenDefinition.Ausweis,
        ScreenDefinition.Blutwerte,
        ScreenDefinition.Vorrat,
        ScreenDefinition.Einstellungen
    )

    val currentRoute = currentRoute(navController = navController)

    BubbleNavigationBar {
        navigationItems.forEach { navigationItem ->
            BubbleNavigationBarItem(
                selected = currentRoute == navigationItem.route,
                onClick = {
                    navController.navigate(navigationItem.route)
                },
                icon = navigationItem.iconId,
                title = navigationItem.name,
                selectedColor = androidx.compose.ui.graphics.Color.Red
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}