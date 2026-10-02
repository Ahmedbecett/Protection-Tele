package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.CleaningServices
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shield

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.ui.screens.AISecurityScreen
import com.example.ui.screens.AntivirusScreen
import com.example.ui.screens.CleanerScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.OptimizerScreen
import com.example.ui.screens.PrivacyScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.CyberBackground
import com.example.ui.theme.CyberPrimary
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.DangerRed
import com.example.ui.theme.MyApplicationTheme
import com.example.util.AppStrings
import com.example.viewmodel.MainSecurityViewModel
import kotlinx.coroutines.flow.collectLatest

enum class MainNavTab {
    DASHBOARD,
    ANTIVIRUS,
    CLEANER,
    AI_ADVISOR,
    OPTIMIZER,
    PRIVACY,
    SETTINGS
}


class MainActivity : ComponentActivity() {

    private val viewModel: MainSecurityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val language by viewModel.currentLanguage.collectAsState()
            val layoutDirection = if (language == AppLanguage.ARABIC) LayoutDirection.Rtl else LayoutDirection.Ltr

            CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                MyApplicationTheme {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainSecurityViewModel) {
    var currentTab by remember { mutableStateOf(MainNavTab.DASHBOARD) }
    val language by viewModel.currentLanguage.collectAsState()
    val activeThreats by viewModel.activeThreats.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbarMessage.collectLatest { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    // Handle back button on sub-screens to return to Dashboard
    if (currentTab != MainNavTab.DASHBOARD) {
        BackHandler {
            currentTab = MainNavTab.DASHBOARD
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(CyberBackground),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar(
                containerColor = CyberSurface,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .background(CyberSurface)
                    .navigationBarsPadding()
                    .testTag("main_bottom_nav")
            ) {
                // 1. Dashboard Tab
                NavigationBarItem(
                    selected = currentTab == MainNavTab.DASHBOARD,
                    onClick = { currentTab = MainNavTab.DASHBOARD },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == MainNavTab.DASHBOARD) Icons.Filled.Shield else Icons.Outlined.Shield,
                            contentDescription = AppStrings.get("tab_dashboard", language),
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = AppStrings.get("tab_dashboard", language),
                            fontSize = 10.sp,
                            fontWeight = if (currentTab == MainNavTab.DASHBOARD) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyberPrimary,
                        selectedTextColor = CyberPrimary,
                        indicatorColor = CyberPrimary.copy(alpha = 0.18f),
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        unselectedTextColor = Color.White.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.testTag("nav_tab_dashboard")
                )

                // 2. Antivirus Tab
                NavigationBarItem(
                    selected = currentTab == MainNavTab.ANTIVIRUS,
                    onClick = { currentTab = MainNavTab.ANTIVIRUS },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (activeThreats.isNotEmpty()) {
                                    Badge(
                                        containerColor = DangerRed,
                                        contentColor = Color.White
                                    ) {
                                        Text("${activeThreats.size}")
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (currentTab == MainNavTab.ANTIVIRUS) Icons.Filled.Security else Icons.Outlined.Security,
                                contentDescription = AppStrings.get("tab_antivirus", language),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    },
                    label = {
                        Text(
                            text = AppStrings.get("tab_antivirus", language),
                            fontSize = 10.sp,
                            fontWeight = if (currentTab == MainNavTab.ANTIVIRUS) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = if (activeThreats.isNotEmpty()) DangerRed else CyberPrimary,
                        selectedTextColor = if (activeThreats.isNotEmpty()) DangerRed else CyberPrimary,
                        indicatorColor = if (activeThreats.isNotEmpty()) DangerRed.copy(alpha = 0.18f) else CyberPrimary.copy(alpha = 0.18f),
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        unselectedTextColor = Color.White.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.testTag("nav_tab_antivirus")
                )

                // 3. Cleaner Tab
                NavigationBarItem(
                    selected = currentTab == MainNavTab.CLEANER,
                    onClick = { currentTab = MainNavTab.CLEANER },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == MainNavTab.CLEANER) Icons.Filled.CleaningServices else Icons.Outlined.CleaningServices,
                            contentDescription = AppStrings.get("tab_cleaner", language),
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = AppStrings.get("tab_cleaner", language),
                            fontSize = 10.sp,
                            fontWeight = if (currentTab == MainNavTab.CLEANER) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyberPrimary,
                        selectedTextColor = CyberPrimary,
                        indicatorColor = CyberPrimary.copy(alpha = 0.18f),
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        unselectedTextColor = Color.White.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.testTag("nav_tab_cleaner")
                )

                // 4. AI Security Advisor Tab
                NavigationBarItem(
                    selected = currentTab == MainNavTab.AI_ADVISOR,
                    onClick = { currentTab = MainNavTab.AI_ADVISOR },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == MainNavTab.AI_ADVISOR) Icons.Filled.AutoAwesome else Icons.Outlined.AutoAwesome,
                            contentDescription = AppStrings.get("tab_ai", language),
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = AppStrings.get("tab_ai", language),
                            fontSize = 10.sp,
                            fontWeight = if (currentTab == MainNavTab.AI_ADVISOR) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyberPrimary,
                        selectedTextColor = CyberPrimary,
                        indicatorColor = CyberPrimary.copy(alpha = 0.18f),
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        unselectedTextColor = Color.White.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.testTag("nav_tab_ai")
                )

                // 5. Optimizer Tab
                NavigationBarItem(
                    selected = currentTab == MainNavTab.OPTIMIZER,
                    onClick = { currentTab = MainNavTab.OPTIMIZER },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == MainNavTab.OPTIMIZER) Icons.Filled.Memory else Icons.Outlined.Memory,
                            contentDescription = AppStrings.get("tab_optimizer", language),
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = AppStrings.get("tab_optimizer", language),
                            fontSize = 10.sp,
                            fontWeight = if (currentTab == MainNavTab.OPTIMIZER) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyberPrimary,
                        selectedTextColor = CyberPrimary,
                        indicatorColor = CyberPrimary.copy(alpha = 0.18f),
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        unselectedTextColor = Color.White.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.testTag("nav_tab_optimizer")
                )

                // 6. Settings & Cloud Tab
                NavigationBarItem(
                    selected = currentTab == MainNavTab.SETTINGS,
                    onClick = { currentTab = MainNavTab.SETTINGS },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == MainNavTab.SETTINGS) Icons.Filled.Settings else Icons.Outlined.Settings,
                            contentDescription = AppStrings.get("tab_settings", language),
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    label = {
                        Text(
                            text = AppStrings.get("tab_settings", language),
                            fontSize = 10.sp,
                            fontWeight = if (currentTab == MainNavTab.SETTINGS) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyberPrimary,
                        selectedTextColor = CyberPrimary,
                        indicatorColor = CyberPrimary.copy(alpha = 0.18f),
                        unselectedIconColor = Color.White.copy(alpha = 0.6f),
                        unselectedTextColor = Color.White.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier.testTag("nav_tab_settings")
                )
            }
        }
    ) { innerPadding ->
        when (currentTab) {
            MainNavTab.DASHBOARD -> DashboardScreen(
                viewModel = viewModel,
                onNavigateToAntivirus = { currentTab = MainNavTab.ANTIVIRUS },
                onNavigateToCleaner = { currentTab = MainNavTab.CLEANER },
                onNavigateToOptimizer = { currentTab = MainNavTab.OPTIMIZER },
                onNavigateToPrivacy = { currentTab = MainNavTab.PRIVACY },
                onNavigateToSettings = { currentTab = MainNavTab.SETTINGS },
                onNavigateToAI = { currentTab = MainNavTab.AI_ADVISOR },
                paddingValues = innerPadding
            )
            MainNavTab.ANTIVIRUS -> AntivirusScreen(
                viewModel = viewModel,
                paddingValues = innerPadding
            )
            MainNavTab.CLEANER -> CleanerScreen(
                viewModel = viewModel,
                paddingValues = innerPadding
            )
            MainNavTab.AI_ADVISOR -> AISecurityScreen(
                viewModel = viewModel,
                paddingValues = innerPadding
            )
            MainNavTab.OPTIMIZER -> OptimizerScreen(
                viewModel = viewModel,
                paddingValues = innerPadding
            )
            MainNavTab.PRIVACY -> PrivacyScreen(
                viewModel = viewModel,
                paddingValues = innerPadding
            )
            MainNavTab.SETTINGS -> SettingsScreen(
                viewModel = viewModel,
                paddingValues = innerPadding
            )
        }
    }
}

