package com.rtw.pro.baseline.qa

enum class QaStatus {
    PASS,
    FAIL
}

data class QaCheckItem(
    val id: String,
    val required: Boolean,
    val status: QaStatus,
    val evidence: String = ""
)

data class BaselineQaGateResult(
    val isPassed: Boolean,
    val failedRequiredIds: List<String>
)
