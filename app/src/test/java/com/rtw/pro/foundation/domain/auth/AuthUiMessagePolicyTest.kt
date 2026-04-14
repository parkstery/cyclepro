package com.rtw.pro.foundation.domain.auth

import kotlin.test.Test
import kotlin.test.assertTrue

class AuthUiMessagePolicyTest {
    @Test
    fun messageFor_mapsNetworkErrorToGuidanceMessage() {
        val msg = AuthUiMessagePolicy.messageFor(AuthError.Unknown("network-error"))
        assertTrue(msg.contains("네트워크"))
    }

    @Test
    fun messageFor_mapsTokenExpiredToReloginMessage() {
        val msg = AuthUiMessagePolicy.messageFor(AuthError.TokenExpired)
        assertTrue(msg.contains("다시 로그인"))
    }
}
