package com.rtw.pro.baseline.data.elevation

import com.rtw.pro.baseline.data.common.HttpRequest
import com.rtw.pro.baseline.data.common.HttpResponse
import com.rtw.pro.baseline.data.common.HttpResult
import com.rtw.pro.baseline.data.common.SimpleHttpTransport
import com.rtw.pro.baseline.domain.geo.LatLng
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OpenElevationHttpApiTest {
    @Test
    fun fetchElevations_parsesElevationArray() {
        val api = OpenElevationHttpApi(
            transport = object : SimpleHttpTransport {
                override fun execute(request: HttpRequest): HttpResult<HttpResponse> {
                    val body = """{"results":[{"elevation":101.2},{"elevation":99.8}]}"""
                    return HttpResult.Success(HttpResponse(200, body))
                }
            },
            baseUrl = "https://elevation.example.com"
        )
        val result = api.fetchElevations(
            samples = listOf(LatLng(37.0, 127.0), LatLng(37.1, 127.1))
        )
        assertTrue(result is HttpResult.Success)
        assertEquals(2, result.value.size)
    }
}
