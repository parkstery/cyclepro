package com.rtw.pro.notification.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FcmTokenSyncCoordinatorTest {
    @Test
    fun syncCurrentToken_registersWhenTokenExists() {
        var registered: String? = null
        val coordinator = FcmTokenSyncCoordinator(
            provider = object : FcmTokenProvider {
                override fun currentToken(): String? = "token-123"
            },
            registrar = object : FcmTokenRegistrar {
                override fun register(token: String): Boolean {
                    registered = token
                    return true
                }
            }
        )
        val result = coordinator.syncCurrentToken()
        assertTrue(result.success)
        assertEquals("token-123", registered)
    }

    @Test
    fun syncCurrentToken_returnsTokenUnavailable_whenProviderHasNoToken() {
        val coordinator = FcmTokenSyncCoordinator(
            provider = object : FcmTokenProvider {
                override fun currentToken(): String? = null
            },
            registrar = object : FcmTokenRegistrar {
                override fun register(token: String): Boolean = true
            }
        )
        val result = coordinator.syncCurrentToken()
        assertEquals(false, result.success)
        assertEquals(FcmErrorCode.TOKEN_UNAVAILABLE, result.errorCode)
    }
}
