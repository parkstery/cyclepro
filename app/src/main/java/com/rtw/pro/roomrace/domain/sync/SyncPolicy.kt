package com.rtw.pro.roomrace.domain.sync

data class SyncPolicy(
    val minUploadHz: Double = 1.0,
    val maxUploadHz: Double = 2.0,
    val serverTickHz: Double = 1.0,
    val staleThresholdMs: Long = 3_000L
) {
    fun isUploadHzValid(hz: Double): Boolean = hz in minUploadHz..maxUploadHz
    fun isStale(lastReceivedAtMs: Long, nowMs: Long): Boolean = nowMs - lastReceivedAtMs >= staleThresholdMs
}
