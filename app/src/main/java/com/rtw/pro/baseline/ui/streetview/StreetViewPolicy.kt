package com.rtw.pro.baseline.ui.streetview

data class StreetViewPolicy(
    val getPanoramaTimeoutMs: Long = 6_000L,
    val panoramaViewTimeoutMs: Long = 6_000L,
    val coverageMin: Double = 0.7
)
