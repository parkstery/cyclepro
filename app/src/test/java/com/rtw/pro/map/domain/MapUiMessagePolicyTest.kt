package com.rtw.pro.map.domain

import com.rtw.pro.map.data.MapBindErrorCode
import kotlin.test.Test
import kotlin.test.assertTrue

class MapUiMessagePolicyTest {
    @Test
    fun bindMessage_returnsPermissionMessage_whenDenied() {
        val msg = MapUiMessagePolicy.bindMessage(MapBindErrorCode.LOCATION_PERMISSION_DENIED)
        assertTrue(msg.contains("권한"))
    }
}
