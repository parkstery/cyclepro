package com.rtw.pro.baseline.data.common

data class RetryPolicy(
    val maxAttempts: Int = 2,
    val retryableStatusCodes: Set<Int> = setOf(408, 429, 500, 502, 503, 504)
) {
    fun canRetry(attempt: Int): Boolean = attempt < maxAttempts
    fun isRetryableStatus(statusCode: Int): Boolean = statusCode in retryableStatusCodes
}
