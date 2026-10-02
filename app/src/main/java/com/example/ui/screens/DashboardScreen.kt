package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.ui.components.ActionTile
import com.example.ui.components.MetricGaugeCard
import com.example.ui.components.StatusHeroCard
import com.example.ui.theme.CloudBlue
import com.example.ui.theme.CyberBackground
import com.example.ui.theme.CyberOutline
import com.example.ui.theme.CyberPrimary
import com.example.ui.theme.CyberSecondary
import com.example.ui.theme.CyberSurface
import com.example.ui.theme.CyberSurfaceVariant
import com.example.ui.theme.DangerRed
import com.example.ui.theme.RamPurple
import com.example.ui.theme.SafeGreen
import com.example.ui.theme.WarningAmber
import com.example.util.AppStrings
import com.example.viewmodel.MainSecurityViewModel

@Composable
fun DashboardScreen(
    viewModel: MainSecurityViewModel,
    onNavigateToAntivirus: () -> Unit,
    onNavigateToCleaner: () -> Unit,
    onNavigateToOptimizer: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToAI: () -> Unit = {},
    paddingValues: PaddingValues
) {

    val language by viewModel.currentLanguage.collectAsState()
    val specs by viewModel.systemSpecs.collectAsState()
    val threats by viewModel.activeThreats.collectAsState()

    val flagIcon = when (language) {
        AppLanguage.ARABIC -> "🇸🇦 "
        AppLanguage.FRENCH -> "🇫🇷 "
        AppLanguage.ENGLISH -> "🇬🇧 "
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CyberBackground)
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // App Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = AppStrings.get("app_title", language),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = AppStrings.get("app_subtitle", language),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Language quick toggle chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CyberSurfaceVariant)
                        .border(1.dp, CyberPrimary.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                        .clickable {
                            val nextLang = when (language) {
                                AppLanguage.ARABIC -> AppLanguage.FRENCH
                                AppLanguage.FRENCH -> AppLanguage.ENGLISH
                                AppLanguage.ENGLISH -> AppLanguage.ARABIC
                            }
                            viewModel.setLanguage(nextLang)
                        }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .testTag("language_toggle_chip")
                ) {
                    Text(
                        text = "$flagIcon${language.title}",
                        color = CyberPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Hero Security Status Card
        item {
            StatusHeroCard(
                threatCount = threats.size,
                language = language,
                onQuickScanClick = onNavigateToAntivirus
            )
        }

        // Global Cloud Threat Live Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .clickable { onNavigateToSettings() }
                    .border(1.dp, CloudBlue.copy(alpha = 0.4f), RoundedCornerShape(18.dp)),
                colors = CardDefaults.cardColors(containerColor = CyberSurface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(CloudBlue.copy(alpha = 0.15f), Color.Transparent)
                            )
                        )
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(CloudBlue.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Public,
                            contentDescription = null,
                            tint = CloudBlue,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = AppStrings.get("cloud_server_title", language),
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(SafeGreen)
                            )
                        }
                        Text(
                            text = "${viewModel.currentCloudRegion.regionName} • 14.8M Signatures",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.CloudDone,
                        contentDescription = null,
                        tint = SafeGreen,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Sentinel AI Security Advisor Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onNavigateToAI() }
                    .border(
                        1.dp,
                        Brush.horizontalGradient(listOf(CyberPrimary, CyberSecondary)),
                        RoundedCornerShape(16.dp)
                    )
                    .testTag("dashboard_ai_advisor_banner"),
                colors = CardDefaults.cardColors(containerColor = CyberSurface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(CyberPrimary.copy(alpha = 0.25f), CyberSecondary.copy(alpha = 0.2f))
                                )
                            )
                            .border(1.dp, CyberPrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = CyberPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = AppStrings.get("ai_advisor_title", language),
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(CyberPrimary.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "AI 3.5",
                                    color = CyberPrimary,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = AppStrings.get("ai_advisor_desc", language),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp,
                            maxLines = 1
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(CyberPrimary)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "START",
                            color = Color(0xFF001F26),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }


        // Active threat warning if any
        if (threats.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { onNavigateToAntivirus() }
                        .border(1.dp, DangerRed, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = DangerRed.copy(alpha = 0.15f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "Threat warning",
                            tint = DangerRed,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${threats.size} " + AppStrings.get("threats_detected", language),
                                fontWeight = FontWeight.Bold,
                                color = DangerRed,
                                fontSize = 14.sp
                            )
                            Text(
                                text = AppStrings.get("status_danger_desc", language),
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Real-Time System Metrics Grid
        item {
            Text(
                text = "Statistiques & Diagnostic Système",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricGaugeCard(
                    title = AppStrings.get("ram_usage", language),
                    valueText = "${specs.usedRamMb} / ${specs.totalRamMb} Mo",
                    subText = "${specs.freeRamMb} Mo Libres",
                    percent = specs.ramPercent,
                    accentColor = RamPurple,
                    icon = Icons.Default.Memory,
                    onClick = onNavigateToOptimizer,
                    modifier = Modifier.weight(1f)
                )

                MetricGaugeCard(
                    title = AppStrings.get("storage_usage", language),
                    valueText = String.format("%.1f / %.1f Go", specs.usedStorageGb, specs.totalStorageGb),
                    subText = String.format("%.1f Go Disponibles", specs.freeStorageGb),
                    percent = specs.storagePercent,
                    accentColor = CyberPrimary,
                    icon = Icons.Default.Storage,
                    onClick = onNavigateToCleaner,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Secondary Metrics Row (CPU Temp & Network)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MetricGaugeCard(
                    title = AppStrings.get("cpu_temp", language),
                    valueText = String.format("%.1f °C", specs.cpuTempCelsius),
                    subText = if (specs.cpuTempCelsius > 38f) "Chaud - Refroidir" else "Température Normale",
                    percent = ((specs.cpuTempCelsius / 60f) * 100).toInt().coerceIn(10, 100),
                    accentColor = if (specs.cpuTempCelsius > 38f) WarningAmber else CyberSecondary,
                    icon = Icons.Default.Thermostat,
                    onClick = onNavigateToOptimizer,
                    modifier = Modifier.weight(1f)
                )

                MetricGaugeCard(
                    title = AppStrings.get("network_security", language),
                    valueText = if (specs.isWifiSecure) "Sécurisé" else "Public",
                    subText = specs.activeNetworkName,
                    percent = if (specs.isWifiSecure) 100 else 60,
                    accentColor = if (specs.isWifiSecure) SafeGreen else WarningAmber,
                    icon = Icons.Default.Wifi,
                    onClick = onNavigateToAntivirus,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Fast Security & Cleaning Actions
        item {
            Text(
                text = "Actions & Outils Rapides",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        item {
            ActionTile(
                title = AppStrings.get("action_clean", language),
                subtitle = AppStrings.get("cleaner_desc", language),
                icon = Icons.Default.CleaningServices,
                iconColor = CyberPrimary,
                onClick = onNavigateToCleaner,
                testTag = "quick_clean_tile"
            )
        }

        item {
            ActionTile(
                title = AppStrings.get("action_boost", language),
                subtitle = AppStrings.get("optimizer_title", language),
                icon = Icons.Default.Memory,
                iconColor = RamPurple,
                onClick = onNavigateToOptimizer,
                testTag = "quick_boost_tile"
            )
        }

        item {
            ActionTile(
                title = AppStrings.get("privacy_title", language),
                subtitle = AppStrings.get("privacy_desc", language),
                icon = Icons.Default.Security,
                iconColor = CyberSecondary,
                onClick = onNavigateToPrivacy,
                testTag = "quick_privacy_tile"
            )
        }

        item {
            ActionTile(
                title = AppStrings.get("settings_title", language),
                subtitle = AppStrings.get("settings_subtitle", language),
                icon = Icons.Default.Settings,
                iconColor = CloudBlue,
                onClick = onNavigateToSettings,
                testTag = "quick_settings_tile"
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
