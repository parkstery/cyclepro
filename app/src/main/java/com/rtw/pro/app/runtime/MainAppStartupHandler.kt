package com.rtw.pro.app.runtime

import com.rtw.pro.map.data.MapProviderConfig
import com.rtw.pro.map.data.StreetViewProviderConfig

class MainAppStartupHandler(
    private val orchestrator: AppRuntimeOrchestrator
) {
    fun onCreate(
        mapConfig: MapProviderConfig,
        streetViewConfig: StreetViewProviderConfig
    ): RuntimeState {
        return orchestrator.onAppLaunch(mapConfig, streetViewConfig)
    }
}
