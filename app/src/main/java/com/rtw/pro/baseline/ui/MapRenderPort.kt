package com.rtw.pro.baseline.ui

/**
 * UI platform adapter for actual map render call.
 */
fun interface MapRenderPort {
    fun renderMap(): MapRenderResult
}
