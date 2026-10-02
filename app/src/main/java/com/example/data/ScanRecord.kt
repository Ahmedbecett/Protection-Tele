package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scan_records")
data class ScanRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val scanType: String, // ANTIVIRUS, CLEANER, RAM_BOOST, CPU_COOL
    val status: String,   // SAFE, THREATS_FOUND, CLEANED, OPTIMIZED
    val summary: String,
    val itemsAffected: Int = 0,
    val bytesCleared: Long = 0L
)
