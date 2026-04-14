package com.rtw.pro.baseline.data.routing

import kotlin.test.Test
import kotlin.test.assertEquals

class RouteModeTest {
    @Test
    fun fromUiMode_mapsAliases() {
        assertEquals(RouteMode.CYCLING, RouteMode.fromUiMode("bike"))
        assertEquals(RouteMode.FOOT, RouteMode.fromUiMode("walk"))
        assertEquals(RouteMode.DRIVING, RouteMode.fromUiMode("unknown"))
    }

    @Test
    fun toOsrmProfilePath_mapsExpectedPath() {
        assertEquals("routed-car", RouteMode.DRIVING.toOsrmProfilePath())
        assertEquals("routed-bike", RouteMode.CYCLING.toOsrmProfilePath())
        assertEquals("routed-foot", RouteMode.FOOT.toOsrmProfilePath())
    }
}
