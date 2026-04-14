package com.rtw.pro.baseline.domain.metrics

import com.rtw.pro.baseline.domain.geo.GeoMath
import com.rtw.pro.baseline.domain.geo.LatLng

data class BaselineRideMetrics(
    val distanceMeters: Double,
    val durationSeconds: Double,
    val averageSpeedKmh: Double
)

object BaselineRideMetricsPipeline {
    fun calculate(path: List<LatLng>, fallbackDurationSeconds: Double): BaselineRideMetrics {
        val cumulative = GeoMath.cumulativeDistanceMeters(path)
        val distance = cumulative.lastOrNull() ?: 0.0
        val duration = if (fallbackDurationSeconds > 0) fallbackDurationSeconds else 1.0
        val averageSpeedKmh = (distance / duration) * 3.6

        return BaselineRideMetrics(
            distanceMeters = distance,
            durationSeconds = duration,
            averageSpeedKmh = averageSpeedKmh
        )
    }
}
