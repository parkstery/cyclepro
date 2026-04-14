package com.rtw.pro.roomrace.domain.sync

import kotlin.math.max

data class PredictionPolicy(
    val maxPredictMs: Long = 2_000L
)

object DisconnectPrediction {
    fun predictDistance(
        lastDistanceMeters: Double,
        lastSpeedMps: Double,
        disconnectElapsedMs: Long,
        policy: PredictionPolicy = PredictionPolicy()
    ): Double {
        val clampedMs = max(0L, minOf(disconnectElapsedMs, policy.maxPredictMs))
        val predictedDelta = lastSpeedMps * (clampedMs / 1000.0)
        return lastDistanceMeters + predictedDelta
    }
}
