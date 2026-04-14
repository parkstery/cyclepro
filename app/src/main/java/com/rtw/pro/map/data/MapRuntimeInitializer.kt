package com.rtw.pro.map.data

enum class MapRuntimeStatus {
    READY,
    MAP_KEY_MISSING,
    STREETVIEW_CONFIG_INVALID
}

data class MapRuntimeResult(
    val status: MapRuntimeStatus,
    val message: String
)

class MapRuntimeInitializer(
    private val mapConfig: MapProviderConfig,
    private val streetViewConfig: StreetViewProviderConfig
) {
    fun initialize(): MapRuntimeResult {
        if (!mapConfig.isReady()) {
            return MapRuntimeResult(
                status = MapRuntimeStatus.MAP_KEY_MISSING,
                message = "Maps API key is missing"
            )
        }
        if (!streetViewConfig.isValid()) {
            return MapRuntimeResult(
                status = MapRuntimeStatus.STREETVIEW_CONFIG_INVALID,
                message = "StreetView timeout config is invalid"
            )
        }
        return MapRuntimeResult(
            status = MapRuntimeStatus.READY,
            message = "Map/StreetView runtime is ready"
        )
    }
}
