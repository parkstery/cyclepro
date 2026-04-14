package com.rtw.pro.notification.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FcmTopicSubscriptionManagerTest {
    @Test
    fun subscribeEventTopic_delegatesToClient() {
        var called = false
        val manager = FcmTopicSubscriptionManager(
            client = object : FcmSubscriptionClient {
                override fun subscribe(topic: String): Boolean {
                    called = true
                    return true
                }

                override fun unsubscribe(topic: String): Boolean = true
            }
        )

        assertTrue(manager.subscribeEventTopic("daily-20h"))
        assertTrue(called)
    }

    @Test
    fun subscribeEventTopicDetailed_returnsMappedError() {
        val manager = FcmTopicSubscriptionManager(
            client = object : FcmSubscriptionClient {
                override fun subscribe(topic: String): Boolean = false
                override fun unsubscribe(topic: String): Boolean = true
                override fun subscribeDetailed(topic: String): FcmTopicSubscriptionResult {
                    return FcmTopicSubscriptionResult(
                        success = false,
                        topic = topic,
                        action = FcmTopicAction.SUBSCRIBE,
                        errorCode = FcmErrorCode.NETWORK_ERROR
                    )
                }
            }
        )
        val result = manager.subscribeEventTopicDetailed("daily-20h")
        assertEquals(false, result.success)
        assertEquals(FcmErrorCode.NETWORK_ERROR, result.errorCode)
    }
}
