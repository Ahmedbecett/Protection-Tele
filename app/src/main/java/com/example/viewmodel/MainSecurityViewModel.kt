package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.ScanRecord
import com.example.data.SecurityRepository
import com.example.data.ThreatItem
import com.example.model.AppLanguage
import com.example.model.AppPrivacyAudit
import com.example.model.JunkItem
import com.example.model.ScanStage
import com.example.model.SecuritySettings
import com.example.model.SystemSpecs
import com.example.service.AntivirusScanner
import com.example.service.CloudServerRegion
import com.example.service.CloudSyncResult
import com.example.service.CloudThreatIntelligenceService
import com.example.service.DeviceCleanerManager
import com.example.service.ScanProgressEvent
import com.example.service.SystemOptimizerManager
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainSecurityViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = SecurityRepository(database.securityDao())

    private val antivirusScanner = AntivirusScanner(application)
    private val cleanerManager = DeviceCleanerManager(application)
    private val optimizerManager = SystemOptimizerManager(application)
    private val cloudService = CloudThreatIntelligenceService()
    private val aiAdvisorService = com.example.service.GeminiSecurityAdvisorService()

    // AI Advisor State
    private val _aiChatMessages = MutableStateFlow<List<com.example.service.AIChatMessage>>(emptyList())
    val aiChatMessages: StateFlow<List<com.example.service.AIChatMessage>> = _aiChatMessages.asStateFlow()

    private val _isAILoading = MutableStateFlow(false)
    val isAILoading: StateFlow<Boolean> = _isAILoading.asStateFlow()

    private val _customGeminiApiKey = MutableStateFlow("")
    val customGeminiApiKey: StateFlow<String> = _customGeminiApiKey.asStateFlow()

    // Language state
    private val _currentLanguage = MutableStateFlow(AppLanguage.ARABIC)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    // Real-time specs
    private val _systemSpecs = MutableStateFlow(optimizerManager.getRealTimeSpecs(cleanerManager))
    val systemSpecs: StateFlow<SystemSpecs> = _systemSpecs.asStateFlow()

    // Comprehensive Settings
    private val _settings = MutableStateFlow(SecuritySettings())
    val settings: StateFlow<SecuritySettings> = _settings.asStateFlow()

    // Cloud Threat Intelligence state
    private val _isSyncingCloud = MutableStateFlow(false)
    val isSyncingCloud: StateFlow<Boolean> = _isSyncingCloud.asStateFlow()

    private val _cloudSyncResult = MutableStateFlow<CloudSyncResult?>(null)
    val cloudSyncResult: StateFlow<CloudSyncResult?> = _cloudSyncResult.asStateFlow()

    val currentCloudRegion: CloudServerRegion
        get() = cloudService.getActiveRegion()

    val cloudSignaturesCount: Long
        get() = cloudService.getSignaturesCount()

    val cloudDbVersion: String
        get() = cloudService.getDatabaseVersion()

    // Antivirus scanning state
    private val _scanStage = MutableStateFlow<ScanStage>(ScanStage.Idle)
    val scanStage: StateFlow<ScanStage> = _scanStage.asStateFlow()

    private val _includeTestMalware = MutableStateFlow(false)
    val includeTestMalware: StateFlow<Boolean> = _includeTestMalware.asStateFlow()

    private val _liveScanLog = MutableStateFlow("Prêt pour l'analyse sécurisée")
    val liveScanLog: StateFlow<String> = _liveScanLog.asStateFlow()

    // Threats from Room
    val activeThreats: StateFlow<List<ThreatItem>> = repository.activeThreats
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val scanRecords: StateFlow<List<ScanRecord>> = repository.allScanRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Cleaner state
    private val _junkItems = MutableStateFlow<List<JunkItem>>(emptyList())
    val junkItems: StateFlow<List<JunkItem>> = _junkItems.asStateFlow()

    private val _isCleaning = MutableStateFlow(false)
    val isCleaning: StateFlow<Boolean> = _isCleaning.asStateFlow()

    private val _lastCleanedBytes = MutableStateFlow<Long?>(null)
    val lastCleanedBytes: StateFlow<Long?> = _lastCleanedBytes.asStateFlow()

    // Optimizer actions state
    private val _isBoostingRam = MutableStateFlow(false)
    val isBoostingRam: StateFlow<Boolean> = _isBoostingRam.asStateFlow()

    private val _ramFreedMb = MutableStateFlow<Long?>(null)
    val ramFreedMb: StateFlow<Long?> = _ramFreedMb.asStateFlow()

    private val _isCoolingCpu = MutableStateFlow(false)
    val isCoolingCpu: StateFlow<Boolean> = _isCoolingCpu.asStateFlow()

    private val _cpuDegreesCooled = MutableStateFlow<Float?>(null)
    val cpuDegreesCooled: StateFlow<Float?> = _cpuDegreesCooled.asStateFlow()

    // Privacy apps
    private val _privacyAudits = MutableStateFlow<List<AppPrivacyAudit>>(emptyList())
    val privacyAudits: StateFlow<List<AppPrivacyAudit>> = _privacyAudits.asStateFlow()

    // Export audit report dialog text
    private val _auditReportText = MutableStateFlow<String?>(null)
    val auditReportText: StateFlow<String?> = _auditReportText.asStateFlow()

    // UI Toast or SnackBar Events
    private val _snackbarMessage = MutableSharedFlow<String>()
    val snackbarMessage: SharedFlow<String> = _snackbarMessage.asSharedFlow()

    init {
        refreshSystemSpecs()
        loadJunkItems()
        loadPrivacyAudits()
    }

    fun setLanguage(lang: AppLanguage) {
        _currentLanguage.value = lang
    }

    fun toggleTestMalware(enable: Boolean) {
        _includeTestMalware.value = enable
    }

    fun refreshSystemSpecs() {
        viewModelScope.launch {
            _systemSpecs.value = optimizerManager.getRealTimeSpecs(cleanerManager)
        }
    }

    // Cloud Threat Server actions
    fun syncWithCloudServers() {
        if (_isSyncingCloud.value) return
        viewModelScope.launch {
            _isSyncingCloud.value = true
            val result = cloudService.testServerConnectionAndSync()
            _cloudSyncResult.value = result
            _isSyncingCloud.value = false

            repository.insertScanRecord(
                ScanRecord(
                    scanType = "CLOUD_SYNC",
                    status = if (result.isSuccess) "SAFE" else "WARNING",
                    summary = "Cloud Sentinel Sync (${result.latencyMs}ms) : ${result.serverNode}",
                    itemsAffected = 0
                )
            )

            _snackbarMessage.emit("Serveur Cloud Synchronisé (${result.latencyMs} ms) - ${result.signatureCount} signatures")
        }
    }

    fun setCloudRegion(region: CloudServerRegion) {
        cloudService.setServerRegion(region)
        _settings.value = _settings.value.copy(selectedRegionCode = region.code)
        syncWithCloudServers()
    }

    fun dismissCloudSyncDialog() {
        _cloudSyncResult.value = null
    }

    // Settings modifiers
    fun updateRealTimeProtection(enabled: Boolean) {
        _settings.value = _settings.value.copy(realTimeProtection = enabled)
    }

    fun updateCloudThreatNetwork(enabled: Boolean) {
        _settings.value = _settings.value.copy(cloudThreatNetwork = enabled)
    }

    fun updateSafeWebPhishing(enabled: Boolean) {
        _settings.value = _settings.value.copy(safeWebPhishing = enabled)
    }

    fun updateAutoDailyScan(enabled: Boolean) {
        _settings.value = _settings.value.copy(autoDailyScan = enabled)
    }

    fun updateSmartJunkAlert(enabled: Boolean) {
        _settings.value = _settings.value.copy(smartJunkAlert = enabled)
    }

    fun updateTurboRamBoost(enabled: Boolean) {
        _settings.value = _settings.value.copy(turboRamBoost = enabled)
    }

    fun updateHeuristicLevel(level: String) {
        _settings.value = _settings.value.copy(heuristicLevel = level)
    }

    fun generateSecurityAuditReport() {
        val specs = _systemSpecs.value
        val threats = activeThreats.value
        val cloud = cloudService

        val report = buildString {
            appendLine("=== PROTECTION TÉLÉPHONE - RAPPORT D'AUDIT SÉCURITÉ 2026 ===")
            appendLine("Date : ${java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.US).format(java.util.Date())}")
            appendLine("Statut Global : ${if (threats.isEmpty()) "SÉCURISÉ (100%)" else "ATTENTION (${threats.size} Menaces)"}")
            appendLine("--------------------------------------------------")
            appendLine("RÉSEAU CLOUD MONDIAL :")
            appendLine("- Serveur Actif : ${cloud.getActiveRegion().regionName}")
            appendLine("- Emplacement : ${cloud.getActiveRegion().location}")
            appendLine("- Version Base Virale : ${cloud.getDatabaseVersion()}")
            appendLine("- Signatures Indexées : ${cloud.getSignaturesCount()}")
            appendLine("--------------------------------------------------")
            appendLine("ÉTAT DE L'APPAREIL :")
            appendLine("- RAM Utilisée : ${specs.usedRamMb} Mo / ${specs.totalRamMb} Mo (${specs.ramPercent}%)")
            appendLine("- Température CPU : ${String.format("%.1f", specs.cpuTempCelsius)} °C")
            appendLine("- Stockage : ${String.format("%.1f", specs.usedStorageGb)} Go / ${String.format("%.1f", specs.totalStorageGb)} Go")
            appendLine("- Sécurité Réseau : ${specs.activeNetworkName}")
            appendLine("--------------------------------------------------")
            appendLine("MENACES ACTIVES : ${threats.size}")
            for (t in threats) {
                appendLine("- [${t.severity}] ${t.appName} (${t.packageName}) : ${t.description}")
            }
            appendLine("==================================================")
        }
        _auditReportText.value = report
    }

    fun dismissAuditReport() {
        _auditReportText.value = null
    }

    fun startAntivirusScan() {
        if (_scanStage.value is ScanStage.Scanning) return

        viewModelScope.launch {
            val testEnabled = _includeTestMalware.value
            antivirusScanner.startDeepScan(includeTestMalware = testEnabled).collect { event ->
                when (event) {
                    is ScanProgressEvent.StageChanged -> {
                        _liveScanLog.value = event.stageTitle
                        _scanStage.value = ScanStage.Scanning(
                            currentTarget = event.stageTitle,
                            progressPercent = event.percent,
                            scannedItemsCount = (event.percent * 1.5).toInt(),
                            totalItemsToScan = 150,
                            currentPhaseText = event.stageTitle
                        )
                    }
                    is ScanProgressEvent.Progress -> {
                        _liveScanLog.value = event.currentItem
                        _scanStage.value = ScanStage.Scanning(
                            currentTarget = event.currentItem,
                            progressPercent = event.percent,
                            scannedItemsCount = event.scannedCount,
                            totalItemsToScan = event.totalCount,
                            currentPhaseText = "Scan : ${event.currentItem}"
                        )
                    }
                    is ScanProgressEvent.Completed -> {
                        _scanStage.value = ScanStage.Finished(
                            scannedItemsCount = event.scannedCount,
                            threatsFoundCount = event.threats.size,
                            durationMs = event.durationMs
                        )
                        for (threat in event.threats) {
                            repository.insertThreat(threat)
                        }

                        val status = if (event.threats.isNotEmpty()) "THREATS_FOUND" else "SAFE"
                        repository.insertScanRecord(
                            ScanRecord(
                                scanType = "ANTIVIRUS",
                                status = status,
                                summary = if (event.threats.isNotEmpty())
                                    "${event.threats.size} menace(s) détectée(s)"
                                else
                                    "Appareil sain et sécurisé",
                                itemsAffected = event.threats.size
                            )
                        )

                        _snackbarMessage.emit(
                            if (event.threats.isEmpty())
                                "Analyse terminée : Aucun virus détecté !"
                            else
                                "Attention : ${event.threats.size} risque(s) identifié(s) !"
                        )
                    }
                }
            }
        }
    }

    fun resolveThreat(threatId: Long) {
        viewModelScope.launch {
            repository.markThreatResolved(threatId)
            _snackbarMessage.emit("Menace désinfectée et neutralisée avec succès.")
            refreshSystemSpecs()
        }
    }

    fun whitelistThreat(threatId: Long) {
        viewModelScope.launch {
            repository.markThreatWhitelisted(threatId)
            _snackbarMessage.emit("Élément ajouté à la liste blanche autorisée.")
        }
    }

    fun resolveAllThreats() {
        viewModelScope.launch {
            repository.resolveAllThreats()
            repository.insertScanRecord(
                ScanRecord(
                    scanType = "ANTIVIRUS",
                    status = "SAFE",
                    summary = "Toutes les menaces ont été neutralisées avec succès."
                )
            )
            _snackbarMessage.emit("Appareil assaini : Toutes les menaces ont été neutralisées.")
            refreshSystemSpecs()
        }
    }

    fun resetScanState() {
        _scanStage.value = ScanStage.Idle
    }

    fun loadJunkItems() {
        viewModelScope.launch {
            _junkItems.value = cleanerManager.analyzeJunkFiles()
        }
    }

    fun toggleJunkSelection(itemId: String) {
        val currentList = _junkItems.value.map { item ->
            if (item.id == itemId) item.copy(isSelected = !item.isSelected) else item
        }
        _junkItems.value = currentList
    }

    fun cleanSelectedJunk() {
        val selected = _junkItems.value.filter { it.isSelected }
        if (selected.isEmpty()) return

        viewModelScope.launch {
            _isCleaning.value = true
            val bytesCleaned = cleanerManager.cleanSelectedJunk(selected)
            _lastCleanedBytes.value = bytesCleaned

            val mbCleaned = (bytesCleaned / (1024 * 1024)).toInt()
            repository.insertScanRecord(
                ScanRecord(
                    scanType = "CLEANER",
                    status = "CLEANED",
                    summary = "Nettoyage réussi : $mbCleaned Mo libérés",
                    itemsAffected = selected.size,
                    bytesCleared = bytesCleaned
                )
            )

            _junkItems.value = cleanerManager.analyzeJunkFiles().map { it.copy(isSelected = false) }
            _isCleaning.value = false
            refreshSystemSpecs()
            _snackbarMessage.emit("Nettoyage terminé : $mbCleaned Mo d'espace libérés !")
        }
    }

    fun dismissCleanDialog() {
        _lastCleanedBytes.value = null
    }

    fun boostRam() {
        if (_isBoostingRam.value) return
        viewModelScope.launch {
            _isBoostingRam.value = true
            val freedMb = optimizerManager.boostMemory()
            _ramFreedMb.value = freedMb
            _isBoostingRam.value = false

            repository.insertScanRecord(
                ScanRecord(
                    scanType = "RAM_BOOST",
                    status = "OPTIMIZED",
                    summary = "Boost RAM : $freedMb Mo de mémoire libérés",
                    bytesCleared = freedMb * 1024 * 1024
                )
            )
            refreshSystemSpecs()
            _snackbarMessage.emit("Boost terminé : $freedMb Mo libérés !")
        }
    }

    fun dismissRamDialog() {
        _ramFreedMb.value = null
    }

    fun coolCpu() {
        if (_isCoolingCpu.value) return
        viewModelScope.launch {
            _isCoolingCpu.value = true
            val degreesCooled = optimizerManager.coolCpu()
            _cpuDegreesCooled.value = degreesCooled
            _isCoolingCpu.value = false

            repository.insertScanRecord(
                ScanRecord(
                    scanType = "CPU_COOL",
                    status = "OPTIMIZED",
                    summary = "Refroidisseur CPU : Température réduite de $degreesCooled°C"
                )
            )
            refreshSystemSpecs()
            _snackbarMessage.emit("CPU refroidi : -${degreesCooled}°C !")
        }
    }

    fun dismissCpuDialog() {
        _cpuDegreesCooled.value = null
    }

    fun loadPrivacyAudits() {
        viewModelScope.launch {
            _privacyAudits.value = optimizerManager.auditAppPermissions()
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
            _snackbarMessage.emit("Historique effacé.")
        }
    }

    // --- AI Security Advisor Actions ---

    fun askAI(question: String) {
        val trimmed = question.trim()
        if (trimmed.isBlank() || _isAILoading.value) return

        val userMsg = com.example.service.AIChatMessage(
            sender = com.example.service.MessageSender.USER,
            text = trimmed
        )
        _aiChatMessages.value = _aiChatMessages.value + userMsg

        viewModelScope.launch {
            _isAILoading.value = true
            val specs = _systemSpecs.value
            val contextInfo = "RAM: ${specs.usedRamMb}/${specs.totalRamMb} MB, Threats: ${activeThreats.value.size}, Cloud Signatures: ${cloudSignaturesCount}, Server Node: ${currentCloudRegion.regionName}"
            
            val answer = aiAdvisorService.consultAI(
                userPrompt = trimmed,
                currentLanguage = _currentLanguage.value,
                deviceContextInfo = contextInfo
            )

            val aiMsg = com.example.service.AIChatMessage(
                sender = com.example.service.MessageSender.AI_ADVISOR,
                text = answer
            )
            _aiChatMessages.value = _aiChatMessages.value + aiMsg
            _isAILoading.value = false
        }
    }

    fun setCustomGeminiApiKey(key: String) {
        _customGeminiApiKey.value = key.trim()
        aiAdvisorService.customApiKey = key.trim()
    }

    fun clearAIChat() {
        _aiChatMessages.value = emptyList()
    }
}

