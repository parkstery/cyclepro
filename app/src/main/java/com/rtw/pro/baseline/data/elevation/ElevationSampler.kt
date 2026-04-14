package com.rtw.pro.baseline.data.elevation

import com.rtw.pro.baseline.domain.geo.LatLng
import kotlin.math.max
import kotlin.math.round

object ElevationSampler {
    fun recommendedSampleCount(totalDistanceMeters: Double): Int {
        return when {
            totalDistanceMeters <= 5_000.0 -> 100
            totalDistanceMeters <= 20_000.0 -> 120
            else -> 160
        }
    }

    fun sampleByEvenIndex(path: List<LatLng>, sampleCount: Int): List<LatLng> {
        if (path.isEmpty()) return emptyList()
        if (path.size <= sampleCount) return path

        val count = max(2, sampleCount)
        val lastIndex = path.lastIndex.toDouble()
        return (0 until count).map { idx ->
            val ratio = idx.toDouble() / (count - 1).toDouble()
            val targetIndex = round(lastIndex * ratio).toInt()
            path[targetIndex]
        }
    }

    fun cacheKey(samples: List<LatLng>): String {
        return samples.joinToString("|") { point ->
            "${point.lat.format5()},${point.lng.format5()}"
        }
    }

    private fun Double.format5(): String = String.format("%.5f", this)
}
