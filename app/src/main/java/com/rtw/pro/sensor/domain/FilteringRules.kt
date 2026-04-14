package com.rtw.pro.sensor.domain

import kotlin.math.abs

data class FilteringRules(
    val maxSpeedKmh: Double = 90.0,
    val spikeThresholdKmh: Double = 20.0,
    val movingAverageWindow: Int = 5,
    val dropoutMs: Long = 2000L
)

class SensorSignalFilter(
    private val rules: FilteringRules = FilteringRules()
) {
    private val speedWindow = ArrayDeque<Double>()
    private var lastAcceptedSpeed = 0.0
    private var lastTimestampMs = 0L

    fun apply(input: CscMeasurement): CscMeasurement {
        var speed = input.speedKmh.coerceAtMost(rules.maxSpeedKmh)
        if (abs(speed - lastAcceptedSpeed) > rules.spikeThresholdKmh && lastTimestampMs != 0L) {
            speed = (speed + lastAcceptedSpeed) / 2.0
        }

        speedWindow.addLast(speed)
        while (speedWindow.size > rules.movingAverageWindow) speedWindow.removeFirst()
        val averaged = speedWindow.average()

        lastAcceptedSpeed = averaged
        lastTimestampMs = input.timestampMs
        return input.copy(speedKmh = averaged)
    }

    fun onNoSignal(nowMs: Long): Double {
        if (lastTimestampMs == 0L) return 0.0
        val elapsed = nowMs - lastTimestampMs
        if (elapsed >= rules.dropoutMs) {
            lastAcceptedSpeed = 0.0
            speedWindow.clear()
            return 0.0
        }
        return lastAcceptedSpeed
    }
}
