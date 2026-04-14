package com.rtw.pro.baseline.ui.streetview

/**
 * Applies fallback state to UI and verifies layout integrity.
 */
class StreetViewTransitionCoordinator(
    private val fallbackCoordinator: StreetViewFallbackCoordinator,
    private val layoutPort: StreetViewLayoutPort
) {
    fun apply(coverageRatio: Double, reason: StreetViewFailureReason?): StreetViewTransitionResult {
        val state = fallbackCoordinator.resolve(coverageRatio, reason)
        layoutPort.switchTo(state.mode)
        val stable = layoutPort.isLayoutStable()
        return StreetViewTransitionResult(
            targetMode = state.mode,
            layoutStable = stable,
            message = if (stable) state.message else "레이아웃 안정성 검증 실패"
        )
    }
}
