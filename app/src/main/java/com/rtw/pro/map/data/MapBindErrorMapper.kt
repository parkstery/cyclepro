package com.rtw.pro.map.data

enum class MapBindErrorCode {
    MAP_API_KEY_MISSING,
    STREETVIEW_CONFIG_INVALID,
    LOCATION_PERMISSION_DENIED,
    MAP_SDK_INIT_FAILED,
    STREETVIEW_SDK_INIT_FAILED,
    UNKNOWN
}

object MapBindErrorMapper {
    fun fromReason(reason: String): MapBindErrorCode {
        return when (reason) {
            "map-api-key-missing" -> MapBindErrorCode.MAP_API_KEY_MISSING
            "streetview-config-invalid" -> MapBindErrorCode.STREETVIEW_CONFIG_INVALID
            "location-permission-denied" -> MapBindErrorCode.LOCATION_PERMISSION_DENIED
            "map-sdk-init-failed" -> MapBindErrorCode.MAP_SDK_INIT_FAILED
            "streetview-sdk-init-failed" -> MapBindErrorCode.STREETVIEW_SDK_INIT_FAILED
            else -> MapBindErrorCode.UNKNOWN
        }
    }
}
