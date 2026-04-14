package com.rtw.pro.map.data

import kotlin.test.Test
import kotlin.test.assertEquals

class MapRuntimeInitializerTest {
    @Test
    fun initialize_returnsReady_whenConfigsAreValid() {
        val initializer = MapRuntimeInitializer(
            mapConfig = MapProviderConfig(mapsApiKey = "key", streetViewEnabled = true),
            streetViewConfig = StreetViewProviderConfig(timeoutMs = 6000L)
        )
        val result = initializer.initialize()
        assertEquals(MapRuntimeStatus.READY, result.status)
    }
}
