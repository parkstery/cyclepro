package com.rtw.pro.notification.data

data class FcmRuntimeConfig(
    val defaultTopicPrefix: String,
    val retries: Int = 2
) {
    fun isValid(): Boolean {
        return defaultTopicPrefix.isNotBlank() && retries >= 0
    }
}

enum class PushDeliveryStatus {
    DELIVERED,
    FAILED,
    RETRY_EXHAUSTED
}

data class PushDeliveryResult(
    val status: PushDeliveryStatus,
    val attempts: Int
)
