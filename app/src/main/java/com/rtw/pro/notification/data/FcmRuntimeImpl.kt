package com.rtw.pro.notification.data

/**
 * FCM SDK runtime skeleton.
 */
class FcmTokenProviderImpl : FcmTokenProvider {
    override fun currentToken(): String? {
        // TODO: Read current token from FirebaseMessaging.
        return null
    }
}

class FcmTokenRegistrarImpl : FcmTokenRegistrar {
    private var backendClient: FcmTokenBackendClient? = null

    fun attachBackendClient(client: FcmTokenBackendClient) {
        backendClient = client
    }

    override fun register(token: String): Boolean {
        return registerDetailed(token).success
    }

    override fun registerDetailed(token: String): FcmTokenSyncResult {
        val client = backendClient ?: return FcmTokenSyncResult(
            success = false,
            token = token,
            errorCode = FcmErrorCode.UNKNOWN
        )
        val response = client.registerToken(token)
        val ok = response.statusCode in 200..299
        return FcmTokenSyncResult(
            success = ok,
            token = token,
            errorCode = if (ok) null else FcmErrorMapper.fromHttpStatus(response.statusCode)
        )
    }
}

data class FcmBackendResponse(
    val statusCode: Int
)

interface FcmTokenBackendClient {
    fun registerToken(token: String): FcmBackendResponse
}

class FcmSubscriptionClientImpl : FcmSubscriptionClient {
    private var backendClient: FcmTopicBackendClient? = null

    fun attachBackendClient(client: FcmTopicBackendClient) {
        backendClient = client
    }

    override fun subscribe(topic: String): Boolean {
        // TODO: FirebaseMessaging.getInstance().subscribeToTopic(topic)
        return subscribeDetailed(topic).success
    }

    override fun unsubscribe(topic: String): Boolean {
        // TODO: FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
        return unsubscribeDetailed(topic).success
    }

    override fun subscribeDetailed(topic: String): FcmTopicSubscriptionResult {
        val client = backendClient ?: return FcmTopicSubscriptionResult(
            success = false,
            topic = topic,
            action = FcmTopicAction.SUBSCRIBE,
            errorCode = FcmErrorCode.UNKNOWN
        )
        val response = client.subscribe(topic)
        val ok = response.statusCode in 200..299
        return FcmTopicSubscriptionResult(
            success = ok,
            topic = topic,
            action = FcmTopicAction.SUBSCRIBE,
            errorCode = if (ok) null else FcmErrorMapper.fromHttpStatus(response.statusCode)
        )
    }

    override fun unsubscribeDetailed(topic: String): FcmTopicSubscriptionResult {
        val client = backendClient ?: return FcmTopicSubscriptionResult(
            success = false,
            topic = topic,
            action = FcmTopicAction.UNSUBSCRIBE,
            errorCode = FcmErrorCode.UNKNOWN
        )
        val response = client.unsubscribe(topic)
        val ok = response.statusCode in 200..299
        return FcmTopicSubscriptionResult(
            success = ok,
            topic = topic,
            action = FcmTopicAction.UNSUBSCRIBE,
            errorCode = if (ok) null else FcmErrorMapper.fromHttpStatus(response.statusCode)
        )
    }
}

interface FcmTopicBackendClient {
    fun subscribe(topic: String): FcmBackendResponse
    fun unsubscribe(topic: String): FcmBackendResponse
}
