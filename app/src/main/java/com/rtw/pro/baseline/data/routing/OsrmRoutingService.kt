package com.rtw.pro.baseline.data.routing

import com.rtw.pro.baseline.data.common.HttpResult
import com.rtw.pro.baseline.data.common.RetryExecutor
import com.rtw.pro.baseline.data.common.RetryPolicy
import com.rtw.pro.baseline.domain.geo.LatLng

class OsrmRoutingService(
    private val api: OsrmRoutingApi,
    private val retryPolicy: RetryPolicy = RetryPolicy(maxAttempts = 2)
) {
    fun getRoute(mode: RouteMode, waypoints: List<LatLng>): HttpResult<RoutePath> {
        val result = RetryExecutor.execute(retryPolicy) { _ ->
            api.fetchRoute(mode, waypoints)
        }
        return when (result) {
            is HttpResult.Success -> HttpResult.Success(OsrmRouteParser.parse(result.value))
            is HttpResult.HttpError -> result
            is HttpResult.NetworkError -> result
        }
    }
}
