package com.rtw.pro.sensor.domain

data class VirtualInputState(
    val speedKmh: Double,
    val cadenceRpm: Double
)

object VirtualInputProvider {
    fun fromSlider(speedKmh: Double): VirtualInputState {
        val cadence = (speedKmh * 3.0).coerceIn(0.0, 120.0)
        return VirtualInputState(speedKmh = speedKmh, cadenceRpm = cadence)
    }
}
