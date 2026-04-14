package com.rtw.pro.baseline.ui.streetview

/**
 * UI adapter boundary for StreetView <-> Map layout switching.
 */
interface StreetViewLayoutPort {
    fun switchTo(mode: StreetViewMode)
    fun isLayoutStable(): Boolean
}
