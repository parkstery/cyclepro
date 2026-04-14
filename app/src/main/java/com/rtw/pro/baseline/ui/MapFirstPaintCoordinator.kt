package com.rtw.pro.baseline.ui

/**
 * Coordinates first paint attempts and user-facing failure guidance.
 */
class MapFirstPaintCoordinator(
    private val renderPort: MapRenderPort,
    private val policy: MapRenderPolicy = MapRenderPolicy()
) {
    fun execute(
        onRetryScheduled: ((nextAttempt: Int, delayMs: Long) -> Unit)? = null
    ): MapFirstPaintUiState {
        var attempt = 1
        while (true) {
            val result = renderPort.renderMap()
            if (result is MapRenderResult.Success) {
                return MapFirstPaintUiState(
                    status = MapFirstPaintUiState.Status.READY,
                    attempt = attempt
                )
            }

            val failure = result as MapRenderResult.Failure
            if (policy.canRetry(attempt)) {
                val nextAttempt = attempt + 1
                onRetryScheduled?.invoke(nextAttempt, policy.retryDelayMs)
                attempt += 1
                continue
            }

            return MapFirstPaintUiState(
                status = MapFirstPaintUiState.Status.FAILED,
                attempt = attempt,
                userMessage = buildFailureMessage(failure.reason)
            )
        }
    }

    private fun buildFailureMessage(reason: String): String {
        return "지도를 불러오지 못했습니다. 네트워크 상태를 확인하고 다시 시도해 주세요. (사유: $reason)"
    }
}
