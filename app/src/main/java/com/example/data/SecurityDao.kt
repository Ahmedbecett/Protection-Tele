package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SecurityDao {
    @Query("SELECT * FROM scan_records ORDER BY timestamp DESC")
    fun getAllScanRecords(): Flow<List<ScanRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScanRecord(record: ScanRecord): Long

    @Query("DELETE FROM scan_records WHERE id = :id")
    suspend fun deleteScanRecord(id: Long)

    @Query("DELETE FROM scan_records")
    suspend fun clearAllScanRecords()

    @Query("SELECT * FROM threat_items WHERE isResolved = 0 AND isWhitelisted = 0 ORDER BY detectedDate DESC")
    fun getActiveThreats(): Flow<List<ThreatItem>>

    @Query("SELECT * FROM threat_items ORDER BY detectedDate DESC")
    fun getAllThreats(): Flow<List<ThreatItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertThreat(threat: ThreatItem): Long

    @Update
    suspend fun updateThreat(threat: ThreatItem)

    @Query("UPDATE threat_items SET isResolved = 1 WHERE id = :id")
    suspend fun markThreatResolved(id: Long)

    @Query("UPDATE threat_items SET isWhitelisted = 1 WHERE id = :id")
    suspend fun markThreatWhitelisted(id: Long)

    @Query("UPDATE threat_items SET isResolved = 1 WHERE isResolved = 0")
    suspend fun resolveAllThreats()
}
