package com.rtw.pro.sensor.domain

enum class SensorConnectionState {
    IDLE,
    SCANNING,
    CONNECTING,
    CONNECTED,
    RECONNECTING,
    FALLBACK_VIRTUAL
}

data class CscMeasurement(
    val speedKmh: Double,
    val cadenceRpm: Double,
    val timestampMs: Long
)

data class SensorDevice(
    val id: String,
    val name: String,
    val isSupported: Boolean
)
