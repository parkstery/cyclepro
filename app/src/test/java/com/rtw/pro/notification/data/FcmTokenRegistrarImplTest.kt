package com.rtw.pro.notification.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FcmTokenRegistrarImplTest {
    @Test
    fun registerDetailed_mapsHttpErrorToFcmErrorCode() {
        val registrar = FcmTokenRegistrarImpl().apply {
            attachBackendClient(
                object : FcmTokenBackendClient {
                    override fun registerToken(token: String): FcmBackendResponse {
                        return FcmBackendResponse(statusCode = 401)
                    }
                }
            )
        }

        val result = registrar.registerDetailed("token-1")
        assertEquals(false, result.success)
        assertEquals(FcmErrorCode.UNAUTHORIZED, result.errorCode)
    }

    @Test
    fun registerDetailed_returnsSuccess_on2xx() {
        val registrar = FcmTokenRegistrarImpl().apply {
            attachBackendClient(
                object : FcmTokenBackendClient {
                    override fun registerToken(token: String): FcmBackendResponse {
                        return FcmBackendResponse(statusCode = 204)
                    }
                }
            )
        }

        val result = registrar.registerDetailed("token-1")
        assertTrue(result.success)
        assertEquals(null, result.errorCode)
    }
}
