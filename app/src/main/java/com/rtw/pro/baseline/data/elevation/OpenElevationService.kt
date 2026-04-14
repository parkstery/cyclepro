package com.rtw.pro.baseline.data.elevation

import com.rtw.pro.baseline.data.common.HttpResult
import com.rtw.pro.baseline.data.common.RetryExecutor
import com.rtw.pro.baseline.data.common.RetryPolicy
import com.rtw.pro.baseline.domain.elevation.ElevationProfile
import com.rtw.pro.baseline.domain.elevation.ElevationProfileCalculator
import com.rtw.pro.baseline.domain.geo.LatLng

class OpenElevationService(
    private val api: OpenElevationApi,
    private val retryPolicy: RetryPolicy = RetryPolicy(maxAttempts = 2)
) {
    fun getProfile(routePoints: List<LatLng>, totalDistanceMeters: Double): HttpResult<ElevationProfile> {
        val sampleCount = ElevationSampler.recommendedSampleCount(totalDistanceMeters)
        val samples = ElevationSampler.sampleByEvenIndex(routePoints, sampleCount)
        val result = RetryExecutor.execute(retryPolicy) { _ ->
            api.fetchElevations(samples)
        }
        return when (result) {
            is HttpResult.Success -> {
                HttpResult.Success(ElevationProfileCalculator.build(result.value))
            }
            is HttpResult.HttpError -> result
            is HttpResult.NetworkError -> result
        }
    }
}
