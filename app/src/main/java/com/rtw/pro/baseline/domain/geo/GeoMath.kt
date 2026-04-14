package com.rtw.pro.baseline.domain.geo

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

object GeoMath {
    private const val EARTH_RADIUS_M = 6_371_000.0

    fun distanceMeters(a: LatLng, b: LatLng): Double {
        val dLat = Math.toRadians(b.lat - a.lat)
        val dLon = Math.toRadians(b.lng - a.lng)
        val x = sin(dLat / 2).pow(2.0) +
            cos(Math.toRadians(a.lat)) * cos(Math.toRadians(b.lat)) * sin(dLon / 2).pow(2.0)
        val c = 2 * atan2(sqrt(x), sqrt(1 - x))
        return EARTH_RADIUS_M * c
    }

    fun headingDegrees(from: LatLng, to: LatLng): Double {
        val dLon = Math.toRadians(to.lng - from.lng)
        val y = sin(dLon) * cos(Math.toRadians(to.lat))
        val x = cos(Math.toRadians(from.lat)) * sin(Math.toRadians(to.lat)) -
            sin(Math.toRadians(from.lat)) * cos(Math.toRadians(to.lat)) * cos(dLon)
        val bearing = Math.toDegrees(atan2(y, x))
        return (bearing + 360.0) % 360.0
    }

    fun cumulativeDistanceMeters(path: List<LatLng>): List<Double> {
        if (path.isEmpty()) return emptyList()
        val cumulative = ArrayList<Double>(path.size)
        var sum = 0.0
        cumulative += 0.0
        for (i in 1 until path.size) {
            sum += distanceMeters(path[i - 1], path[i])
            cumulative += sum
        }
        return cumulative
    }
}
