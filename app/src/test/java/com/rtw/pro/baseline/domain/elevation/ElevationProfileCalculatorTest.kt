package com.rtw.pro.baseline.domain.elevation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ElevationProfileCalculatorTest {
    @Test
    fun build_interpolatesMissingValues() {
        val profile = ElevationProfileCalculator.build(
            listOf(10.0, null, null, 16.0, null)
        )

        assertEquals(5, profile.points.size)
        assertEquals(10.0, profile.points[0])
        assertEquals(12.0, profile.points[1], 0.0001)
        assertEquals(14.0, profile.points[2], 0.0001)
        assertEquals(16.0, profile.points[3], 0.0001)
        assertEquals(16.0, profile.points[4], 0.0001)
        assertTrue(profile.totalAscentMeters > 0.0)
    }
}
