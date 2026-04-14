package com.rtw.pro.roomrace.domain.sync

data class RiderSnapshot(
    val uid: String,
    val distanceMeters: Double,
    val serverTimestampMs: Long
)
