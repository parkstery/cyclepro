package com.rtw.pro.map.data

data class MapProviderConfig(
    val mapsApiKey: String,
    val streetViewEnabled: Boolean = true
) {
    fun isReady(): Boolean = mapsApiKey.isNotBlank()
}

data class StreetViewProviderConfig(
    val timeoutMs: Long = 6000L,
    val fallbackToMapOnly: Boolean = true
) {
    fun isValid(): Boolean = timeoutMs in 1000L..15000L
}
