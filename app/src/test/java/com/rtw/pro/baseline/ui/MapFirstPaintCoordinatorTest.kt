package com.rtw.pro.baseline.ui

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MapFirstPaintCoordinatorTest {

    @Test
    fun execute_returnsReady_whenSecondAttemptSucceeds() {
        var calls = 0
        val renderPort = MapRenderPort {
            calls += 1
            if (calls == 1) {
                MapRenderResult.Failure("timeout")
            } else {
                MapRenderResult.Success
            }
        }

        val coordinator = MapFirstPaintCoordinator(
            renderPort = renderPort,
            policy = MapRenderPolicy(maxAttempts = 3, retryDelayMs = 500L)
        )

        val scheduled = mutableListOf<Pair<Int, Long>>()
        val state = coordinator.execute { nextAttempt, delayMs ->
            scheduled += nextAttempt to delayMs
        }

        assertEquals(MapFirstPaintUiState.Status.READY, state.status)
        assertEquals(2, state.attempt)
        assertEquals(listOf(2 to 500L), scheduled)
    }

    @Test
    fun execute_returnsFailedMessage_whenAllAttemptsFail() {
        val renderPort = MapRenderPort {
            MapRenderResult.Failure("network-unreachable")
        }

        val coordinator = MapFirstPaintCoordinator(
            renderPort = renderPort,
            policy = MapRenderPolicy(maxAttempts = 2, retryDelayMs = 100L)
        )

        val state = coordinator.execute()

        assertEquals(MapFirstPaintUiState.Status.FAILED, state.status)
        assertEquals(2, state.attempt)
        assertTrue(state.userMessage?.contains("다시 시도") == true)
    }
}
