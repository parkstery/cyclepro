package com.rtw.pro.baseline.qa

object BaselineQaGate {
    fun evaluate(items: List<QaCheckItem>): BaselineQaGateResult {
        val failedRequired = items
            .asSequence()
            .filter { it.required && it.status == QaStatus.FAIL }
            .map { it.id }
            .toList()

        return BaselineQaGateResult(
            isPassed = failedRequired.isEmpty(),
            failedRequiredIds = failedRequired
        )
    }
}
