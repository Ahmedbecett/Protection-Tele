package com.example.model

enum class AppLanguage(val code: String, val title: String) {
    ARABIC("ar", "العربية"),
    FRENCH("fr", "Français"),
    ENGLISH("en", "English")
}

enum class SecurityLevel {
    SECURE,
    WARNING,
    DANGER
}

enum class ThreatSeverity {
    LOW,
    MEDIUM,
    HIGH
}

enum class JunkCategoryType {
    APP_CACHE,
    RESIDUAL_FILES,
    LARGE_FILES,
    APK_FILES,
    TEMP_LOGS
}

data class JunkItem(
    val id: String,
    val type: JunkCategoryType,
    val title: String,
    val subtitle: String,
    val sizeBytes: Long,
    var isSelected: Boolean = true
)

data class SystemSpecs(
    val totalRamMb: Long,
    val usedRamMb: Long,
    val freeRamMb: Long,
    val ramPercent: Int,
    val cpuTempCelsius: Float,
    val batteryPercent: Int,
    val batteryStatus: String,
    val batteryTemp: Float,
    val totalStorageGb: Float,
    val usedStorageGb: Float,
    val freeStorageGb: Float,
    val storagePercent: Int,
    val isWifiSecure: Boolean = true,
    val activeNetworkName: String = "Wi-Fi Sécurisé"
)

data class AppPrivacyAudit(
    val packageName: String,
    val appName: String,
    val permissions: List<String>,
    val riskScore: Int, // 1 to 10
    val isSystemApp: Boolean,
    val installSource: String
)

sealed class ScanStage {
    object Idle : ScanStage()
    data class Scanning(
        val currentTarget: String,
        val progressPercent: Int,
        val scannedItemsCount: Int,
        val totalItemsToScan: Int,
        val currentPhaseText: String
    ) : ScanStage()
    data class Finished(
        val scannedItemsCount: Int,
        val threatsFoundCount: Int,
        val durationMs: Long
    ) : ScanStage()
}

data class SecuritySettings(
    val realTimeProtection: Boolean = true,
    val cloudThreatNetwork: Boolean = true,
    val heuristicLevel: String = "Balanced", // Strict, Balanced, Fast
    val safeWebPhishing: Boolean = true,
    val autoDailyScan: Boolean = true,
    val smartJunkAlert: Boolean = true,
    val turboRamBoost: Boolean = false,
    val selectedRegionCode: String = "ANYCAST"
)

