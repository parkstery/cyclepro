package com.rtw.pro.notification.data

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Single-thread executor so [Tasks.await] for FCM never runs on the UI thread.
 */
private val fcmSdkExecutor = Executors.newSingleThreadExecutor { runnable ->
    Thread(runnable, "rtw-fcm-sdk").apply { isDaemon = true }
}

/**
 * FCM SDK runtime: token read and topic subscribe/unsubscribe via Firebase Messaging.
 * Does not require interactive Google Sign-In (uses app Firebase configuration only).
 */
class FcmTokenProviderImpl : FcmTokenProvider {
    override fun currentToken(): String? {
        val future = fcmSdkExecutor.submit<String?> {
            try {
                FirebaseApp.getInstance()
                Tasks.await(FirebaseMessaging.getInstance().getToken(), 20, TimeUnit.SECONDS)
            } catch (_: IllegalStateException) {
                null
            } catch (_: Exception) {
                null
            }
        }
        return try {
            future.get(25, TimeUnit.SECONDS)
        } catch (_: Exception) {
            future.cancel(true)
            null
        }
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
        val client = backendClient
        if (client == null) {
            // No HTTP backend yet: treat non-blank FCM token as synced for local smoke / dashboard.
            return FcmTokenSyncResult(
                success = token.isNotBlank(),
                token = token,
                errorCode = if (token.isBlank()) FcmErrorCode.TOKEN_UNAVAILABLE else null
            )
        }
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
        return subscribeDetailed(topic).success
    }

    override fun unsubscribe(topic: String): Boolean {
        return unsubscribeDetailed(topic).success
    }

    override fun subscribeDetailed(topic: String): FcmTopicSubscriptionResult {
        val client = backendClient
        if (client != null) {
            val response = client.subscribe(topic)
            val ok = response.statusCode in 200..299
            return FcmTopicSubscriptionResult(
                success = ok,
                topic = topic,
                action = FcmTopicAction.SUBSCRIBE,
                errorCode = if (ok) null else FcmErrorMapper.fromHttpStatus(response.statusCode)
            )
        }
        return runFirebaseTopic(topic, FcmTopicAction.SUBSCRIBE) { FirebaseMessaging.getInstance().subscribeToTopic(it) }
    }

    override fun unsubscribeDetailed(topic: String): FcmTopicSubscriptionResult {
        val client = backendClient
        if (client != null) {
            val response = client.unsubscribe(topic)
            val ok = response.statusCode in 200..299
            return FcmTopicSubscriptionResult(
                success = ok,
                topic = topic,
                action = FcmTopicAction.UNSUBSCRIBE,
                errorCode = if (ok) null else FcmErrorMapper.fromHttpStatus(response.statusCode)
            )
        }
        return runFirebaseTopic(topic, FcmTopicAction.UNSUBSCRIBE) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(it)
        }
    }

    private fun runFirebaseTopic(
        topic: String,
        action: FcmTopicAction,
        taskFactory: (String) -> Task<Void>
    ): FcmTopicSubscriptionResult {
        if (topic.isBlank()) {
            return FcmTopicSubscriptionResult(
                success = false,
                topic = topic,
                action = action,
                errorCode = FcmErrorCode.UNKNOWN
            )
        }
        val future = fcmSdkExecutor.submit<FcmTopicSubscriptionResult> {
            try {
                try {
                    FirebaseApp.getInstance()
                } catch (_: IllegalStateException) {
                    return@submit FcmTopicSubscriptionResult(
                        success = false,
                        topic = topic,
                        action = action,
                        errorCode = FcmErrorCode.TOKEN_UNAVAILABLE
                    )
                }
                Tasks.await(taskFactory(topic), 30, TimeUnit.SECONDS)
                FcmTopicSubscriptionResult(success = true, topic = topic, action = action, errorCode = null)
            } catch (e: IOException) {
                FcmTopicSubscriptionResult(
                    success = false,
                    topic = topic,
                    action = action,
                    errorCode = FcmErrorCode.NETWORK_ERROR
                )
            } catch (_: Exception) {
                FcmTopicSubscriptionResult(
                    success = false,
                    topic = topic,
                    action = action,
                    errorCode = FcmErrorCode.UNKNOWN
                )
            }
        }
        return try {
            future.get(35, TimeUnit.SECONDS)
        } catch (_: Exception) {
            future.cancel(true)
            FcmTopicSubscriptionResult(
                success = false,
                topic = topic,
                action = action,
                errorCode = FcmErrorCode.UNKNOWN
            )
        }
    }
}

interface FcmTopicBackendClient {
    fun subscribe(topic: String): FcmBackendResponse
    fun unsubscribe(topic: String): FcmBackendResponse
}
