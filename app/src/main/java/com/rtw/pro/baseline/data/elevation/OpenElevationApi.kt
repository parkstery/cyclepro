package com.rtw.pro.baseline.data.elevation

import com.rtw.pro.baseline.data.common.HttpResult
import com.rtw.pro.baseline.domain.geo.LatLng

interface OpenElevationApi {
    fun fetchElevations(samples: List<LatLng>): HttpResult<List<Double?>>
}
