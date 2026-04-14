package com.rtw.pro.baseline.data.elevation

import com.rtw.pro.baseline.domain.geo.LatLng
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ElevationSamplerTest {
    @Test
    fun recommendedSampleCount_returnsExpectedBands() {
        assertEquals(100, ElevationSampler.recommendedSampleCount(4_000.0))
        assertEquals(120, ElevationSampler.recommendedSampleCount(10_000.0))
        assertEquals(160, ElevationSampler.recommendedSampleCount(30_000.0))
    }

    @Test
    fun cacheKey_roundsToFiveDecimals() {
        val key = ElevationSampler.cacheKey(
            listOf(LatLng(37.123456, 127.654321))
        )
        assertEquals("37.12346,127.65432", key)
    }

    @Test
    fun sampleByEvenIndex_returnsRequestedCount() {
        val path = (0..99).map { LatLng(it.toDouble(), it.toDouble()) }
        val sampled = ElevationSampler.sampleByEvenIndex(path, 10)
        assertEquals(10, sampled.size)
        assertTrue(sampled.first() == path.first())
        assertTrue(sampled.last() == path.last())
    }
}
