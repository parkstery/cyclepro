package com.rtw.pro.notification.data

import com.rtw.pro.notification.domain.PushMessage
import kotlin.test.Test
import kotlin.test.assertEquals

class ReliableFcmPushNotifierTest {
    @Test
    fun sendWithRetry_returnsDelivered_whenSecondAttemptSucceeds() {
        var calls = 0
        val fcm = FcmPushNotifier(
            fcmClient = object : FcmClient {
                override fun sendToTopic(topic: String, title: String, body: String): Boolean {
                    calls += 1
                    return calls >= 2
                }
            }
        )
        val reliable = ReliableFcmPushNotifier(
            notifier = fcm,
            config = FcmRuntimeConfig(defaultTopicPrefix = "rtw", retries = 2)
        )
        val result = reliable.sendWithRetry(PushMessage("title", "body", "rtw-20h"))
        assertEquals(PushDeliveryStatus.DELIVERED, result.status)
        assertEquals(2, result.attempts)
    }
}
