package com.rtw.pro.notification.data

import com.rtw.pro.notification.domain.PushMessage

class ReliableFcmPushNotifier(
    private val notifier: FcmPushNotifier,
    private val config: FcmRuntimeConfig = FcmRuntimeConfig(defaultTopicPrefix = "rtw")
) {
    fun sendWithRetry(message: PushMessage): PushDeliveryResult {
        var attempt = 0
        while (attempt <= config.retries) {
            attempt += 1
            if (notifier.send(message)) {
                return PushDeliveryResult(
                    status = PushDeliveryStatus.DELIVERED,
                    attempts = attempt
                )
            }
        }
        return PushDeliveryResult(
            status = PushDeliveryStatus.RETRY_EXHAUSTED,
            attempts = attempt
        )
    }
}
