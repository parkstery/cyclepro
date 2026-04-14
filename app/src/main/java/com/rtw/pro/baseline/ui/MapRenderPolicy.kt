package com.rtw.pro.baseline.ui

/**
 * First paint retry policy for L1-00 Step 1.
 */
data class MapRenderPolicy(
    val maxAttempts: Int = 3,
    val retryDelayMs: Long = 750L
) {
    fun canRetry(attempt: Int): Boolean {
        return attempt < maxAttempts
    }
}
