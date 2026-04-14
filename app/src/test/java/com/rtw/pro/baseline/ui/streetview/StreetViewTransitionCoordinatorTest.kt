package com.rtw.pro.baseline.ui.streetview

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StreetViewTransitionCoordinatorTest {
    @Test
    fun apply_switchesToMapOnly_andPassesWhenLayoutStable() {
        val fallback = StreetViewFallbackCoordinator()
        val layout = FakeLayoutPort(stable = true)
        val coordinator = StreetViewTransitionCoordinator(fallback, layout)

        val result = coordinator.apply(
            coverageRatio = 0.5,
            reason = StreetViewFailureReason.NO_PANORAMA
        )

        assertEquals(StreetViewMode.MAP_ONLY, result.targetMode)
        assertTrue(result.layoutStable)
        assertEquals(StreetViewMode.MAP_ONLY, layout.currentMode)
    }

    @Test
    fun apply_returnsFailureMessage_whenLayoutUnstable() {
        val fallback = StreetViewFallbackCoordinator()
        val layout = FakeLayoutPort(stable = false)
        val coordinator = StreetViewTransitionCoordinator(fallback, layout)

        val result = coordinator.apply(
            coverageRatio = 1.0,
            reason = null
        )

        assertEquals(StreetViewMode.STREETVIEW, result.targetMode)
        assertFalse(result.layoutStable)
        assertTrue(result.message?.contains("안정성") == true)
    }

    private class FakeLayoutPort(
        private val stable: Boolean
    ) : StreetViewLayoutPort {
        var currentMode: StreetViewMode = StreetViewMode.MAP_ONLY

        override fun switchTo(mode: StreetViewMode) {
            currentMode = mode
        }

        override fun isLayoutStable(): Boolean = stable
    }
}
