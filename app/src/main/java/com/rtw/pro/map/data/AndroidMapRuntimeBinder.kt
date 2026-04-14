package com.rtw.pro.map.data

enum class MapPermissionState {
    GRANTED,
    DENIED
}

interface MapPermissionGateway {
    fun locationPermissionState(): MapPermissionState
}

interface GoogleMapSdkGateway {
    fun initialize(apiKey: String): Boolean
}

interface StreetViewSdkGateway {
    fun initialize(timeoutMs: Long): Boolean
}

data class MapBindResult(
    val ready: Boolean,
    val reason: String
)

class AndroidMapRuntimeBinder(
    private val permissionGateway: MapPermissionGateway,
    private val mapGateway: GoogleMapSdkGateway,
    private val streetViewGateway: StreetViewSdkGateway
) {
    fun bind(mapConfig: MapProviderConfig, svConfig: StreetViewProviderConfig): MapBindResult {
        if (!mapConfig.isReady()) return MapBindResult(false, "map-api-key-missing")
        if (!svConfig.isValid()) return MapBindResult(false, "streetview-config-invalid")
        if (permissionGateway.locationPermissionState() == MapPermissionState.DENIED) {
            return MapBindResult(false, "location-permission-denied")
        }

        val mapOk = mapGateway.initialize(mapConfig.mapsApiKey)
        if (!mapOk) return MapBindResult(false, "map-sdk-init-failed")

        if (mapConfig.streetViewEnabled) {
            val svOk = streetViewGateway.initialize(svConfig.timeoutMs)
            if (!svOk) return MapBindResult(false, "streetview-sdk-init-failed")
        }
        return MapBindResult(true, "ready")
    }
}
