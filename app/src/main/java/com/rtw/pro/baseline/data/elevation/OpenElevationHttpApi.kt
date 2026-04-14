package com.rtw.pro.baseline.data.elevation

import com.rtw.pro.baseline.data.common.HttpRequest
import com.rtw.pro.baseline.data.common.HttpResult
import com.rtw.pro.baseline.data.common.SimpleJsonExtractors
import com.rtw.pro.baseline.data.common.SimpleHttpTransport
import com.rtw.pro.baseline.domain.geo.LatLng

class OpenElevationHttpApi(
    private val transport: SimpleHttpTransport,
    private val baseUrl: String
) : OpenElevationApi {
    override fun fetchElevations(samples: List<LatLng>): HttpResult<List<Double?>> {
        if (samples.isEmpty()) return HttpResult.Success(emptyList())
        val locations = samples.joinToString("|") { "${it.lat},${it.lng}" }
        val url = "$baseUrl/api/v1/lookup?locations=$locations"
        return when (val response = transport.execute(HttpRequest(url = url))) {
            is HttpResult.Success -> {
                if (response.value.statusCode !in 200..299) {
                    HttpResult.HttpError(response.value.statusCode, response.value.body)
                } else {
                    val values = SimpleJsonExtractors.extractNumberArrayByObjectKey(
                        json = response.value.body,
                        objectKey = "results",
                        key = "elevation"
                    )
                    if (values.isEmpty()) {
                        HttpResult.HttpError(502, "open-elevation-parse-failed")
                    } else {
                        HttpResult.Success(values.map { it as Double? })
                    }
                }
            }
            is HttpResult.HttpError -> response
            is HttpResult.NetworkError -> response
        }
    }
}
