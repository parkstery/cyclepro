package com.rtw.pro.baseline.qa

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BaselineQaGateTest {
    @Test
    fun evaluate_passes_whenNoRequiredFailures() {
        val result = BaselineQaGate.evaluate(
            listOf(
                QaCheckItem("map-first-paint", required = true, status = QaStatus.PASS),
                QaCheckItem("optional-note", required = false, status = QaStatus.FAIL)
            )
        )

        assertTrue(result.isPassed)
        assertTrue(result.failedRequiredIds.isEmpty())
    }

    @Test
    fun evaluate_fails_whenRequiredItemFailed() {
        val result = BaselineQaGate.evaluate(
            listOf(
                QaCheckItem("streetview-transition", required = true, status = QaStatus.FAIL),
                QaCheckItem("route-retry", required = true, status = QaStatus.PASS)
            )
        )

        assertEquals(false, result.isPassed)
        assertEquals(listOf("streetview-transition"), result.failedRequiredIds)
    }
}
