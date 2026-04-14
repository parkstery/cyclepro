package com.rtw.pro.beta.domain

data class BetaKpiSnapshot(
    val loginSuccessRate: Double,
    val roomJoinSuccessRate: Double,
    val finishRate20m: Double,
    val sensorReconnectSuccessRate: Double,
    val syncLatencyAvgSec: Double,
    val syncLatencyP95Sec: Double,
    val overtakeAccuracyRate: Double,
    val retentionD7Rate: Double
)

enum class BetaDecision {
    GO,
    CONDITIONAL_GO,
    NO_GO
}

data class BetaGateResult(
    val decision: BetaDecision,
    val failedMetrics: List<String>
)
