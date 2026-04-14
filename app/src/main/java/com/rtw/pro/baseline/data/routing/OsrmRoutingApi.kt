package com.rtw.pro.baseline.data.routing

import com.rtw.pro.baseline.data.common.HttpResult
import com.rtw.pro.baseline.domain.geo.LatLng

interface OsrmRoutingApi {
    fun fetchRoute(
        mode: RouteMode,
        waypoints: List<LatLng>
    ): HttpResult<OsrmRouteDto>
}
