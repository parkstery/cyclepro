package com.rtw.pro.notification.domain

import com.rtw.pro.notification.data.FcmErrorCode
import kotlin.test.Test
import kotlin.test.assertTrue

class PushUiMessagePolicyTopicTest {
    @Test
    fun topicSubscriptionMessage_returnsSuccessMessageWhenNoError() {
        val msg = PushUiMessagePolicy.topicSubscriptionMessage(errorCode = null, subscribed = true)
        assertTrue(msg.contains("완료"))
    }

    @Test
    fun topicSubscriptionMessage_returnsNetworkMessageWhenNetworkError() {
        val msg = PushUiMessagePolicy.topicSubscriptionMessage(
            errorCode = FcmErrorCode.NETWORK_ERROR,
            subscribed = true
        )
        assertTrue(msg.contains("네트워크"))
    }
}
