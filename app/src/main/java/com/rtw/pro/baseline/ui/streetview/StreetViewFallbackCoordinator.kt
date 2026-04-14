package com.rtw.pro.baseline.ui.streetview

class StreetViewFallbackCoordinator(
    private val policy: StreetViewPolicy = StreetViewPolicy()
) {
    fun resolve(coverageRatio: Double, reason: StreetViewFailureReason?): StreetViewUiState {
        if (coverageRatio < policy.coverageMin) {
            return StreetViewUiState(
                mode = StreetViewMode.MAP_ONLY,
                message = "거리뷰 커버리지가 낮아 지도 모드로 전환합니다.",
                coverageRatio = coverageRatio
            )
        }
        if (reason == null) {
            return StreetViewUiState(
                mode = StreetViewMode.STREETVIEW,
                coverageRatio = coverageRatio
            )
        }

        return StreetViewUiState(
            mode = StreetViewMode.MAP_ONLY,
            message = failureMessage(reason),
            coverageRatio = coverageRatio
        )
    }

    private fun failureMessage(reason: StreetViewFailureReason): String {
        return when (reason) {
            StreetViewFailureReason.NO_PANORAMA -> "해당 구간은 거리뷰를 지원하지 않아 지도 모드로 전환합니다."
            StreetViewFailureReason.TIMEOUT -> "거리뷰 응답이 지연되어 지도 모드로 전환합니다."
            StreetViewFailureReason.OVER_QUERY_LIMIT -> "거리뷰 호출 제한으로 지도 모드로 전환합니다."
            StreetViewFailureReason.UNKNOWN -> "거리뷰를 불러오지 못해 지도 모드로 전환합니다."
        }
    }
}
