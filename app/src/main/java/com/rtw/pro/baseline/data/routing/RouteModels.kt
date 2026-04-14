package com.rtw.pro.baseline.data.routing

import com.rtw.pro.baseline.domain.geo.LatLng

data class OsrmRouteDto(
    val encodedPolyline: String,
    val distanceMeters: Double,
    val durationSeconds: Double
)

data class RoutePath(
    val points: List<LatLng>,
    val distanceMeters: Double,
    val durationSeconds: Double
)

object OsrmRouteParser {
    fun parse(dto: OsrmRouteDto): RoutePath {
        return RoutePath(
            points = PolylineDecoder.decode(dto.encodedPolyline),
            distanceMeters = dto.distanceMeters,
            durationSeconds = dto.durationSeconds
        )
    }
}
