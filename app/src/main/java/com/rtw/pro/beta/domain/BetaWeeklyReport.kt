package com.rtw.pro.beta.domain

data class BetaWeeklyReport(
    val weekLabel: String,
    val decision: BetaDecision,
    val failedMetrics: List<String>,
    val actionItems: List<String>
)

object BetaWeeklyReportBuilder {
    fun build(
        weekLabel: String,
        snapshots: List<BetaKpiSnapshot>,
        evaluator: BetaGoNoGoEvaluator = BetaGoNoGoEvaluator()
    ): BetaWeeklyReport {
        val gate = evaluator.evaluate(snapshots)
        return BetaWeeklyReport(
            weekLabel = weekLabel,
            decision = gate.decision,
            failedMetrics = gate.failedMetrics,
            actionItems = gate.failedMetrics.map { "원인 분석 및 개선 액션: $it" }
        )
    }
}
