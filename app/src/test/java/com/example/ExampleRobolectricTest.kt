package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppDatabase
import com.example.data.ScanRecord
import com.example.data.ThreatItem
import com.example.model.AppLanguage
import com.example.model.SecuritySettings
import com.example.service.CloudThreatIntelligenceService
import com.example.service.DeviceCleanerManager
import com.example.util.AppStrings
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun readStringFromContext() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Protection Téléphone", appName)
    }

    @Test
    fun testAppStringsLocalization() {
        val arabicTitle = AppStrings.get("app_title", AppLanguage.ARABIC)
        assertEquals("حماية الهاتف", arabicTitle)

        val frenchTitle = AppStrings.get("app_title", AppLanguage.FRENCH)
        assertEquals("Protection Téléphone", frenchTitle)

        val englishTitle = AppStrings.get("app_title", AppLanguage.ENGLISH)
        assertEquals("Protection Téléphone", englishTitle)

        val englishSubtitle = AppStrings.get("app_subtitle", AppLanguage.ENGLISH)
        assertEquals("Advanced Phone Cleaner & Global Antivirus", englishSubtitle)

        val englishSettings = AppStrings.get("settings_title", AppLanguage.ENGLISH)
        assertEquals("Security & App Settings", englishSettings)
    }

    @Test
    fun testRoomDatabasePersistence() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AppDatabase.getDatabase(context)
        val dao = db.securityDao()

        val threat = ThreatItem(
            packageName = "com.test.threat",
            appName = "Malware Test",
            threatType = "TROJAN_PATTERN",
            severity = "HIGH",
            description = "High risk malware signature"
        )
        val threatId = dao.insertThreat(threat)
        assertTrue(threatId > 0)

        val active = dao.getActiveThreats().first()
        assertTrue(active.any { it.packageName == "com.test.threat" })

        // Mark as resolved
        dao.markThreatResolved(threatId)
        val afterResolve = dao.getActiveThreats().first()
        assertTrue(afterResolve.none { it.id == threatId })
    }

    @Test
    fun testDeviceCleanerAnalysis() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val cleaner = DeviceCleanerManager(context)
        val junkList = cleaner.analyzeJunkFiles()

        assertNotNull(junkList)
        assertTrue(junkList.isNotEmpty())
    }

    @Test
    fun testCloudServerSync() = runBlocking {
        val cloudService = CloudThreatIntelligenceService()
        val syncResult = cloudService.testServerConnectionAndSync()

        assertNotNull(syncResult)
        assertTrue(syncResult.isSuccess)
        assertTrue(syncResult.signatureCount > 10000000L)
    }

    @Test
    fun testSecuritySettingsDefaults() {
        val settings = SecuritySettings()
        assertTrue(settings.realTimeProtection)
        assertTrue(settings.cloudThreatNetwork)
        assertEquals("Balanced", settings.heuristicLevel)
    }
}
