package com.rtw.pro.ux.domain.hud

data class HudCoreData(
    val speedKmh: Double,
    val rank: Int,
    val gapAheadMeters: Double,
    val gapBehindMeters: Double,
    val remainingDistanceMeters: Double
)

data class HudAuxData(
    val averageSpeedKmh: Double,
    val elevationDeltaMeters: Double,
    val detailStatsSummary: String
)

data class HudUiState(
    val core: HudCoreData,
    val aux: HudAuxData?,
    val isAuxExpanded: Boolean
)
