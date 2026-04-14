package com.rtw.pro.notification.domain

import com.rtw.pro.notification.data.FcmErrorCode
import kotlin.test.Test
import kotlin.test.assertTrue

class PushUiMessagePolicyTest {
    @Test
    fun tokenSyncMessage_returnsPermissionGuidance_forUnauthorized() {
        val msg = PushUiMessagePolicy.tokenSyncMessage(FcmErrorCode.UNAUTHORIZED)
        assertTrue(msg.contains("권한"))
    }
}
