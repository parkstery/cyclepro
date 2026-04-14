package com.rtw.pro.map.data

import kotlin.test.Test
import kotlin.test.assertEquals

class MapBindErrorMapperTest {
    @Test
    fun fromReason_mapsKnownReasons() {
        assertEquals(MapBindErrorCode.MAP_API_KEY_MISSING, MapBindErrorMapper.fromReason("map-api-key-missing"))
        assertEquals(MapBindErrorCode.LOCATION_PERMISSION_DENIED, MapBindErrorMapper.fromReason("location-permission-denied"))
    }
}
