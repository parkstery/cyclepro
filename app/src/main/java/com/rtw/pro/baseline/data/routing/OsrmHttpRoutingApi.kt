package com.rtw.pro.baseline.data.routing

import com.rtw.pro.baseline.data.common.HttpRequest
import com.rtw.pro.baseline.data.common.HttpResult
import com.rtw.pro.baseline.data.common.SimpleJsonExtractors
import com.rtw.pro.baseline.data.common.SimpleHttpTransport
import com.rtw.pro.baseline.domain.geo.LatLng

class OsrmHttpRoutingApi(
    private val transport: SimpleHttpTransport,
    private val baseUrl: String
) : OsrmRoutingApi {
    override fun fetchRoute(mode: RouteMode, waypoints: List<LatLng>): HttpResult<OsrmRouteDto> {
        if (waypoints.size < 2) return HttpResult.HttpError(400, "waypoints requires >= 2")
        val coords = waypoints.joinToString(";") { "${it.lng},${it.lat}" }
        val url = "$baseUrl/${mode.toOsrmProfilePath()}/route/v1/${mode.name.lowercase()}/$coords?overview=full&geometries=polyline"
        return when (val response = transport.execute(HttpRequest(url = url))) {
            is HttpResult.Success -> {
                if (response.value.statusCode !in 200..299) {
                    HttpResult.HttpError(response.value.statusCode, response.value.body)
                } else {
                    parseRoute(response.value.body)
                        ?.let { HttpResult.Success(it) }
                        ?: HttpResult.HttpError(502, "osrm-parse-failed")
                }
            }
            is HttpResult.HttpError -> response
            is HttpResult.NetworkError -> response
        }
    }

    private fun parseRoute(body: String): OsrmRouteDto? {
        val geometry = SimpleJsonExtractors.extractFirstString(body, "geometry") ?: return null
        val distance = SimpleJsonExtractors.extractFirstNumber(body, "distance") ?: return null
        val duration = SimpleJsonExtractors.extractFirstNumber(body, "duration") ?: return null
        return OsrmRouteDto(
            encodedPolyline = geometry,
            distanceMeters = distance,
            durationSeconds = duration
        )
    }
}
