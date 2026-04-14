package com.rtw.pro.beta.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BetaWeeklyReportBuilderTest {
    @Test
    fun build_generatesActionItems_whenMetricsFail() {
        val report = BetaWeeklyReportBuilder.build(
            weekLabel = "2026-W15",
            snapshots = listOf(
                BetaKpiSnapshot(
                    loginSuccessRate = 0.99,
                    roomJoinSuccessRate = 0.99,
                    finishRate20m = 0.60,
                    sensorReconnectSuccessRate = 0.95,
                    syncLatencyAvgSec = 1.0,
                    syncLatencyP95Sec = 2.0,
                    overtakeAccuracyRate = 0.98,
                    retentionD7Rate = 0.2
                )
            )
        )
        assertEquals(BetaDecision.CONDITIONAL_GO, report.decision)
        assertTrue(report.actionItems.isNotEmpty())
    }
}
