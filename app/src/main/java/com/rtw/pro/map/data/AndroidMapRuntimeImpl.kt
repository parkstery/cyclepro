package com.rtw.pro.map.data

/**
 * Android map/streetview runtime skeleton.
 */
class MapPermissionGatewayImpl : MapPermissionGateway {
    override fun locationPermissionState(): MapPermissionState {
        // TODO: Check ACCESS_FINE_LOCATION runtime permission from Android context.
        return MapPermissionState.DENIED
    }
}

class GoogleMapSdkGatewayImpl : GoogleMapSdkGateway {
    override fun initialize(apiKey: String): Boolean {
        // TODO: Initialize Google Maps SDK with API key and return success/failure.
        return false
    }
}

class StreetViewSdkGatewayImpl : StreetViewSdkGateway {
    override fun initialize(timeoutMs: Long): Boolean {
        // TODO: Initialize StreetView provider and apply timeout policy.
        return false
    }
}
