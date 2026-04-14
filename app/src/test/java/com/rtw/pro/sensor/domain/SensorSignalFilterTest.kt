package com.rtw.pro.sensor.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SensorSignalFilterTest {
    @Test
    fun apply_capsMaxSpeed_andSmoothsSpike() {
        val filter = SensorSignalFilter(
            FilteringRules(maxSpeedKmh = 60.0, spikeThresholdKmh = 10.0, movingAverageWindow = 2)
        )
        filter.apply(CscMeasurement(20.0, 80.0, 1000L))
        val out = filter.apply(CscMeasurement(100.0, 80.0, 1500L))
        assertTrue(out.speedKmh <= 60.0)
    }

    @Test
    fun onNoSignal_returnsZero_afterDropout() {
        val filter = SensorSignalFilter(FilteringRules(dropoutMs = 1000L))
        filter.apply(CscMeasurement(20.0, 70.0, 1000L))
        val speed = filter.onNoSignal(2500L)
        assertEquals(0.0, speed, 0.0001)
    }
}
