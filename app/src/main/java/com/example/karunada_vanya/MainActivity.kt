package com.example.karunada_vanya

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.karunada_vanya.navigation.Screen
import com.example.karunada_vanya.ui.components.DrawerContent
import com.example.karunada_vanya.ui.components.StatusBar
import com.example.karunada_vanya.ui.components.TopBar
import com.example.karunada_vanya.ui.screens.*
import com.example.karunada_vanya.ui.theme.*
import com.example.karunada_vanya.viewmodel.MovementViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KarunadavanyaTheme {
                KarunadaVanyaApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KarunadaVanyaApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    var currentRegion by remember { mutableStateOf("ALL") }
    val movementViewModel: MovementViewModel = viewModel()
    val movementUiState by movementViewModel.uiState.collectAsState()

    // Check if tablet (width >= 600dp)
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (isTablet) {
        // ── TABLET: permanent side drawer ──────────────────────────────
        Row(modifier = Modifier.fillMaxSize()) {
            DrawerContent(
                currentScreen = currentScreen,
                onNavigate = { screen -> currentScreen = screen }
            )
            MainContent(
                currentScreen = currentScreen,
                currentRegion = currentRegion,
                onRegionSelected = { currentRegion = it },
                onMenuClick = {},
                movementViewModel = movementViewModel,
                movementUiState = movementUiState
            )
        }
    } else {
        // ── PHONE: modal navigation drawer ────────────────────────────
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerContainerColor = ForestGreen,
                    modifier = Modifier.width(260.dp)
                ) {
                    DrawerContent(
                        currentScreen = currentScreen,
                        onNavigate = { screen ->
                            currentScreen = screen
                            scope.launch { drawerState.close() }
                        }
                    )
                }
            }
        ) {
            MainContent(
                currentScreen = currentScreen,
                currentRegion = currentRegion,
                onRegionSelected = { currentRegion = it },
                onMenuClick = { scope.launch { drawerState.open() } },
                movementViewModel = movementViewModel,
                movementUiState = movementUiState
            )
        }
    }
}

@Composable
fun MainContent(
    currentScreen: Screen,
    currentRegion: String,
    onRegionSelected: (String) -> Unit,
    onMenuClick: () -> Unit,
    movementViewModel: MovementViewModel,
    movementUiState: com.example.karunada_vanya.viewmodel.MovementUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamWhite)
    ) {
        TopBar(
            currentRegion = currentRegion,
            onRegionSelected = onRegionSelected,
            onMenuClick = onMenuClick
        )

        Divider(color = DividerColor)

        Box(modifier = Modifier.weight(1f)) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "screen_transition"
            ) { screen ->
                when (screen) {
                    Screen.Home -> HomeScreen(
                        activeAlerts = movementUiState.alerts.filter { it.isStillActive() }
                    )
                    Screen.Wiki -> WikiScreen()
                    Screen.Movement -> MovementScreen(viewModel = movementViewModel)
                    Screen.SafeGuide -> SafeGuideScreen()
                    Screen.ForestAudio -> ForestAudioScreen()
                }
            }
        }

        Divider(color = DividerColor)
        StatusBar()
    }
}