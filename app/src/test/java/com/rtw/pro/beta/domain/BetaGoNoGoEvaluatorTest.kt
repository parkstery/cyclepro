package com.rtw.pro.beta.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class BetaGoNoGoEvaluatorTest {
    @Test
    fun evaluate_returnsGo_whenTwoWeeksMeetAllTargets() {
        val evaluator = BetaGoNoGoEvaluator()
        val week = BetaKpiSnapshot(
            loginSuccessRate = 0.99,
            roomJoinSuccessRate = 0.99,
            finishRate20m = 0.75,
            sensorReconnectSuccessRate = 0.92,
            syncLatencyAvgSec = 1.4,
            syncLatencyP95Sec = 2.0,
            overtakeAccuracyRate = 0.96,
            retentionD7Rate = 0.35
        )
        val result = evaluator.evaluate(listOf(week, week))
        assertEquals(BetaDecision.GO, result.decision)
    }

    @Test
    fun evaluate_returnsNoGo_whenCoreMetricsFail() {
        val evaluator = BetaGoNoGoEvaluator()
        val result = evaluator.evaluate(
            listOf(
                BetaKpiSnapshot(
                    loginSuccessRate = 0.90,
                    roomJoinSuccessRate = 0.99,
                    finishRate20m = 0.80,
                    sensorReconnectSuccessRate = 0.95,
                    syncLatencyAvgSec = 1.0,
                    syncLatencyP95Sec = 2.0,
                    overtakeAccuracyRate = 0.98,
                    retentionD7Rate = 0.40
                )
            )
        )
        assertEquals(BetaDecision.NO_GO, result.decision)
    }
}
