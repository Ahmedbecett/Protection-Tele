package com.example.data

import kotlinx.coroutines.flow.Flow

class SecurityRepository(private val securityDao: SecurityDao) {
    val allScanRecords: Flow<List<ScanRecord>> = securityDao.getAllScanRecords()
    val activeThreats: Flow<List<ThreatItem>> = securityDao.getActiveThreats()
    val allThreats: Flow<List<ThreatItem>> = securityDao.getAllThreats()

    suspend fun insertScanRecord(record: ScanRecord): Long =
        securityDao.insertScanRecord(record)

    suspend fun clearHistory() =
        securityDao.clearAllScanRecords()

    suspend fun insertThreat(threat: ThreatItem): Long =
        securityDao.insertThreat(threat)

    suspend fun markThreatResolved(id: Long) =
        securityDao.markThreatResolved(id)

    suspend fun markThreatWhitelisted(id: Long) =
        securityDao.markThreatWhitelisted(id)

    suspend fun resolveAllThreats() =
        securityDao.resolveAllThreats()
}
