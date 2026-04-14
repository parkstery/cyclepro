package com.rtw.pro.baseline.ui

/**
 * Result model for a single map render attempt.
 */
sealed class MapRenderResult {
    data object Success : MapRenderResult()

    data class Failure(
        val reason: String
    ) : MapRenderResult()
}
