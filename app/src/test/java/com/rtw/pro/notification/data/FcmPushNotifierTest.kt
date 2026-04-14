package com.rtw.pro.notification.data

import com.rtw.pro.notification.domain.PushMessage
import kotlin.test.Test
import kotlin.test.assertTrue

class FcmPushNotifierTest {
    @Test
    fun send_delegatesToFcmClient() {
        var called = false
        val notifier = FcmPushNotifier(
            fcmClient = object : FcmClient {
                override fun sendToTopic(topic: String, title: String, body: String): Boolean {
                    called = true
                    return true
                }
            }
        )
        val ok = notifier.send(PushMessage("t", "b", "event-20h"))
        assertTrue(ok)
        assertTrue(called)
    }
}
