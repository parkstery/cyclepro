package com.rtw.pro.beta.domain

data class BetaKpiThresholds(
    val loginSuccessRateMin: Double = 0.98,
    val roomJoinSuccessRateMin: Double = 0.98,
    val finishRate20mMin: Double = 0.70,
    val sensorReconnectSuccessRateMin: Double = 0.90,
    val syncLatencyAvgSecMax: Double = 1.5,
    val syncLatencyP95SecMax: Double = 2.5,
    val overtakeAccuracyRateMin: Double = 0.95,
    val retentionD7RateMin: Double = 0.30
)

class BetaGoNoGoEvaluator(
    private val thresholds: BetaKpiThresholds = BetaKpiThresholds()
) {
    fun evaluate(last2Weeks: List<BetaKpiSnapshot>): BetaGateResult {
        if (last2Weeks.isEmpty()) return BetaGateResult(BetaDecision.NO_GO, listOf("no-data"))
        val latest = last2Weeks.last()
        val failed = failedMetrics(latest)

        val twoWeekSatisfied = last2Weeks.size >= 2 && last2Weeks.takeLast(2).all { failedMetrics(it).isEmpty() }
        if (twoWeekSatisfied) return BetaGateResult(BetaDecision.GO, emptyList())

        if (failed.size <= 2 && failed.none { it in setOf("login", "race-core", "sensor-core") }) {
            return BetaGateResult(BetaDecision.CONDITIONAL_GO, failed)
        }
        return BetaGateResult(BetaDecision.NO_GO, failed)
    }

    private fun failedMetrics(kpi: BetaKpiSnapshot): List<String> {
        val failed = mutableListOf<String>()
        if (kpi.loginSuccessRate < thresholds.loginSuccessRateMin) failed += "login"
        if (kpi.roomJoinSuccessRate < thresholds.roomJoinSuccessRateMin) failed += "race-core"
        if (kpi.finishRate20m < thresholds.finishRate20mMin) failed += "finish-rate"
        if (kpi.sensorReconnectSuccessRate < thresholds.sensorReconnectSuccessRateMin) failed += "sensor-core"
        if (kpi.syncLatencyAvgSec > thresholds.syncLatencyAvgSecMax) failed += "sync-avg"
        if (kpi.syncLatencyP95Sec > thresholds.syncLatencyP95SecMax) failed += "sync-p95"
        if (kpi.overtakeAccuracyRate < thresholds.overtakeAccuracyRateMin) failed += "overtake-accuracy"
        if (kpi.retentionD7Rate < thresholds.retentionD7RateMin) failed += "retention-d7"
        return failed
    }
}
