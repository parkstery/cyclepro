package com.rtw.pro.notification.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FcmSubscriptionClientImplTest {
    @Test
    fun subscribeDetailed_mapsBackendStatusCode() {
        val client = FcmSubscriptionClientImpl().apply {
            attachBackendClient(
                object : FcmTopicBackendClient {
                    override fun subscribe(topic: String): FcmBackendResponse = FcmBackendResponse(503)
                    override fun unsubscribe(topic: String): FcmBackendResponse = FcmBackendResponse(204)
                }
            )
        }
        val result = client.subscribeDetailed("daily-20h")
        assertEquals(false, result.success)
        assertEquals(FcmErrorCode.NETWORK_ERROR, result.errorCode)
    }

    @Test
    fun unsubscribeDetailed_succeedsOn2xx() {
        val client = FcmSubscriptionClientImpl().apply {
            attachBackendClient(
                object : FcmTopicBackendClient {
                    override fun subscribe(topic: String): FcmBackendResponse = FcmBackendResponse(200)
                    override fun unsubscribe(topic: String): FcmBackendResponse = FcmBackendResponse(200)
                }
            )
        }
        val result = client.unsubscribeDetailed("daily-20h")
        assertTrue(result.success)
        assertEquals(null, result.errorCode)
    }
}
