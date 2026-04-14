package com.rtw.pro.baseline.data.routing

import com.rtw.pro.baseline.data.common.HttpRequest
import com.rtw.pro.baseline.data.common.HttpResponse
import com.rtw.pro.baseline.data.common.HttpResult
import com.rtw.pro.baseline.data.common.SimpleHttpTransport
import com.rtw.pro.baseline.domain.geo.LatLng
import kotlin.test.Test
import kotlin.test.assertTrue

class OsrmHttpRoutingApiTest {
    @Test
    fun fetchRoute_parsesGeometryDistanceDuration() {
        val api = OsrmHttpRoutingApi(
            transport = object : SimpleHttpTransport {
                override fun execute(request: HttpRequest): HttpResult<HttpResponse> {
                    val body = """{"routes":[{"geometry":"_p~iF~ps|U_ulLnnqC_mqNvxq`@","distance":1234.5,"duration":321.0}]}"""
                    return HttpResult.Success(HttpResponse(200, body))
                }
            },
            baseUrl = "https://osrm.example.com"
        )
        val result = api.fetchRoute(
            mode = RouteMode.CYCLING,
            waypoints = listOf(LatLng(37.0, 127.0), LatLng(37.1, 127.1))
        )
        assertTrue(result is HttpResult.Success)
        assertTrue(result.value.distanceMeters > 1000.0)
    }
}
