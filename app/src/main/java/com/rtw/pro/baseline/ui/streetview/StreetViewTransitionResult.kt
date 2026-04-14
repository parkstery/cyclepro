package com.rtw.pro.baseline.ui.streetview

data class StreetViewTransitionResult(
    val targetMode: StreetViewMode,
    val layoutStable: Boolean,
    val message: String? = null
)
