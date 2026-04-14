package com.rtw.pro.baseline.ui

/**
 * UI state for first paint + retry flow.
 */
data class MapFirstPaintUiState(
    val status: Status,
    val attempt: Int,
    val userMessage: String? = null
) {
    enum class Status {
        IDLE,
        RENDERING,
        RETRY_SCHEDULED,
        READY,
        FAILED
    }
}
