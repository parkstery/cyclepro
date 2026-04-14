package com.rtw.pro.map.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AndroidMapRuntimeBinderTest {
    @Test
    fun bind_returnsReady_whenPermissionAndSdkInitSucceed() {
        val binder = AndroidMapRuntimeBinder(
            permissionGateway = object : MapPermissionGateway {
                override fun locationPermissionState(): MapPermissionState = MapPermissionState.GRANTED
            },
            mapGateway = object : GoogleMapSdkGateway {
                override fun initialize(apiKey: String): Boolean = true
            },
            streetViewGateway = object : StreetViewSdkGateway {
                override fun initialize(timeoutMs: Long): Boolean = true
            }
        )
        val result = binder.bind(
            mapConfig = MapProviderConfig(mapsApiKey = "key", streetViewEnabled = true),
            svConfig = StreetViewProviderConfig(timeoutMs = 6000L)
        )
        assertTrue(result.ready)
        assertEquals("ready", result.reason)
    }
}
