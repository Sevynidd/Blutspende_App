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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.commandiron.bubble_navigation_bar_compose.BubbleNavigationBar
import com.commandiron.bubble_navigation_bar_compose.BubbleNavigationBarItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Setzen eines Splashscreens
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

@OptIn(ExperimentalMaterial3Api::class)
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
    var selectedIndex by remember { mutableStateOf(0) }

    /*BubbleNavigationBar {
        navigationItems.forEach { navigationItem ->
            BubbleNavigationBarItem(
                selected = currentRoute == navigationItem.route,
                onClick = {
                    //Navigate
                },
                icon = navigationItem.icon,
                title = navigationItem.title,
                selectedColor = navigationItem.selectedColor
            )
        }
    }*/
}

@Composable
fun ButtonHome() {
    Button(onClick = { }) {
        Text("Home")
    }
}

@Composable
fun ButtonAusweis() {
    Button(onClick = { }) {
        Text("Ausweis")
    }
}

@Composable
fun ButtonBlutwerte() {
    Button(onClick = { }) {
        Text("Blutwerte")
    }
}

@Composable
fun ButtonVorrat() {
    Button(onClick = { }) {
        Text("Vorrat")
    }
}

@Composable
fun ButtonEinstellungen() {
    Button(onClick = { }) {
        Text("Einstellungen")
    }
}