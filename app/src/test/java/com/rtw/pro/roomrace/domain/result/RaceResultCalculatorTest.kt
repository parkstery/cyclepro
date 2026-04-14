package com.rtw.pro.roomrace.domain.result

import com.rtw.pro.roomrace.domain.sync.RiderSnapshot
import kotlin.test.Test
import kotlin.test.assertEquals

class RaceResultCalculatorTest {
    @Test
    fun rank_ordersByDistance_thenFinishTime_withDnfLast() {
        val a = RaceResultCalculator.toFinish(
            snapshot = RiderSnapshot("a", 1000.0, 10_000L),
            elapsedTimeSec = 300,
            avgSpeedKmh = 12.0,
            dnf = false
        )
        val b = RaceResultCalculator.toFinish(
            snapshot = RiderSnapshot("b", 1000.0, 9_000L),
            elapsedTimeSec = 299,
            avgSpeedKmh = 12.1,
            dnf = false
        )
        val c = RaceResultCalculator.toFinish(
            snapshot = RiderSnapshot("c", 800.0, 8_000L),
            elapsedTimeSec = 320,
            avgSpeedKmh = 9.0,
            dnf = true
        )

        val ranked = RaceResultCalculator.rank(listOf(a, b, c))
        assertEquals("b", ranked[0].uid)
        assertEquals("a", ranked[1].uid)
        assertEquals("c", ranked[2].uid)
    }
}
