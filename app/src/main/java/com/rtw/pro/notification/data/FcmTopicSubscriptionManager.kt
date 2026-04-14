package com.rtw.pro.notification.data

interface FcmSubscriptionClient {
    fun subscribe(topic: String): Boolean
    fun unsubscribe(topic: String): Boolean
    fun subscribeDetailed(topic: String): FcmTopicSubscriptionResult {
        val ok = subscribe(topic)
        return FcmTopicSubscriptionResult(
            success = ok,
            topic = topic,
            action = FcmTopicAction.SUBSCRIBE,
            errorCode = if (ok) null else FcmErrorCode.UNKNOWN
        )
    }
    fun unsubscribeDetailed(topic: String): FcmTopicSubscriptionResult {
        val ok = unsubscribe(topic)
        return FcmTopicSubscriptionResult(
            success = ok,
            topic = topic,
            action = FcmTopicAction.UNSUBSCRIBE,
            errorCode = if (ok) null else FcmErrorCode.UNKNOWN
        )
    }
}

enum class FcmTopicAction {
    SUBSCRIBE,
    UNSUBSCRIBE
}

data class FcmTopicSubscriptionResult(
    val success: Boolean,
    val topic: String,
    val action: FcmTopicAction,
    val errorCode: FcmErrorCode? = null
)

class FcmTopicSubscriptionManager(
    private val client: FcmSubscriptionClient
) {
    fun subscribeEventTopic(topic: String): Boolean {
        return client.subscribe(topic)
    }

    fun unsubscribeEventTopic(topic: String): Boolean {
        return client.unsubscribe(topic)
    }

    fun subscribeEventTopicDetailed(topic: String): FcmTopicSubscriptionResult {
        return client.subscribeDetailed(topic)
    }

    fun unsubscribeEventTopicDetailed(topic: String): FcmTopicSubscriptionResult {
        return client.unsubscribeDetailed(topic)
    }
}
