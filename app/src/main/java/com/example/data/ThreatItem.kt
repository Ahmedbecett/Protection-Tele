package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "threat_items")
data class ThreatItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val packageName: String,
    val appName: String,
    val threatType: String, // "ADWARE", "SUSPICIOUS_PERMISSIONS", "UNVERIFIED_SOURCE", "TROJAN_PATTERN"
    val severity: String,   // "HIGH", "MEDIUM", "LOW"
    val description: String,
    val detectedDate: Long = System.currentTimeMillis(),
    val isResolved: Boolean = false,
    val isWhitelisted: Boolean = false
)
