package com.rtw.pro.notification.data

import kotlin.test.Test
import kotlin.test.assertEquals

class FcmErrorMapperTest {
    @Test
    fun fromHttpStatus_mapsAuthAndNetworkCodes() {
        assertEquals(FcmErrorCode.UNAUTHORIZED, FcmErrorMapper.fromHttpStatus(401))
        assertEquals(FcmErrorCode.NETWORK_ERROR, FcmErrorMapper.fromHttpStatus(503))
        assertEquals(FcmErrorCode.INVALID_TOKEN, FcmErrorMapper.fromHttpStatus(400))
    }
}
