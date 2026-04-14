package com.rtw.pro.baseline.ui.streetview

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StreetViewFallbackCoordinatorTest {
    @Test
    fun resolve_returnsMapOnly_whenCoverageTooLow() {
        val coordinator = StreetViewFallbackCoordinator(StreetViewPolicy(coverageMin = 0.7))
        val state = coordinator.resolve(coverageRatio = 0.5, reason = null)

        assertEquals(StreetViewMode.MAP_ONLY, state.mode)
        assertTrue(state.message?.contains("커버리지") == true)
    }

    @Test
    fun resolve_returnsMapOnlyWithReasonMessage_whenTimeout() {
        val coordinator = StreetViewFallbackCoordinator()
        val state = coordinator.resolve(
            coverageRatio = 1.0,
            reason = StreetViewFailureReason.TIMEOUT
        )

        assertEquals(StreetViewMode.MAP_ONLY, state.mode)
        assertTrue(state.message?.contains("지연") == true)
    }
}
