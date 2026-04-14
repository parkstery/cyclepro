package com.rtw.pro.baseline.domain

import com.rtw.pro.baseline.domain.elevation.ElevationProfile
import com.rtw.pro.baseline.domain.metrics.BaselineRideMetrics

data class BaselineRouteBundle(
    val metrics: BaselineRideMetrics,
    val elevationProfile: ElevationProfile
)
