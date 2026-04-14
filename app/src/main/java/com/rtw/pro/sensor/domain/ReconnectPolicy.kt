package com.rtw.pro.sensor.domain

data class ReconnectPolicy(
    val maxAttempts: Int = 3,
    val retryIntervalMs: Long = 1500L
) {
    fun canRetry(attempt: Int): Boolean = attempt < maxAttempts
}
