package com.rtw.pro.baseline.domain

import com.rtw.pro.baseline.data.common.HttpResult
import com.rtw.pro.baseline.data.elevation.OpenElevationService
import com.rtw.pro.baseline.data.routing.OsrmRoutingService
import com.rtw.pro.baseline.data.routing.RouteMode
import com.rtw.pro.baseline.domain.geo.LatLng
import com.rtw.pro.baseline.domain.metrics.BaselineRideMetricsPipeline

class BaselineRouteOrchestrator(
    private val routingService: OsrmRoutingService,
    private val elevationService: OpenElevationService
) {
    fun build(mode: RouteMode, waypoints: List<LatLng>): HttpResult<BaselineRouteBundle> {
        val routeResult = routingService.getRoute(mode, waypoints)
        if (routeResult !is HttpResult.Success) return routeResult

        val route = routeResult.value
        val metrics = BaselineRideMetricsPipeline.calculate(route.points, route.durationSeconds)
        val elevationResult = elevationService.getProfile(route.points, route.distanceMeters)
        if (elevationResult !is HttpResult.Success) return elevationResult

        return HttpResult.Success(
            BaselineRouteBundle(
                metrics = metrics,
                elevationProfile = elevationResult.value
            )
        )
    }
}
