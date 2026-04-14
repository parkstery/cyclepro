package com.rtw.pro.baseline.ui.streetview

data class StreetViewUiState(
    val mode: StreetViewMode,
    val message: String? = null,
    val coverageRatio: Double = 1.0
)
