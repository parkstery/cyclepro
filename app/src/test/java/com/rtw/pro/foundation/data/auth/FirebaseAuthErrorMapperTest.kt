package com.rtw.pro.foundation.data.auth

import kotlin.test.Test
import kotlin.test.assertEquals

class FirebaseAuthErrorMapperTest {
    @Test
    fun fromFirebaseErrorCode_mapsKnownCodes() {
        assertEquals(
            FirebaseSignInErrorCode.INVALID_GOOGLE_TOKEN,
            FirebaseAuthErrorMapper.fromFirebaseErrorCode("ERROR_INVALID_CREDENTIAL")
        )
        assertEquals(
            FirebaseSignInErrorCode.NETWORK_ERROR,
            FirebaseAuthErrorMapper.fromFirebaseErrorCode("ERROR_NETWORK_REQUEST_FAILED")
        )
        assertEquals(
            FirebaseSignInErrorCode.USER_DISABLED,
            FirebaseAuthErrorMapper.fromFirebaseErrorCode("ERROR_USER_DISABLED")
        )
        assertEquals(
            FirebaseSignInErrorCode.TOKEN_EXPIRED,
            FirebaseAuthErrorMapper.fromFirebaseErrorCode("ERROR_USER_TOKEN_EXPIRED")
        )
    }

    @Test
    fun fromFirebaseErrorCode_returnsUnknown_forNullOrUnexpected() {
        assertEquals(FirebaseSignInErrorCode.UNKNOWN, FirebaseAuthErrorMapper.fromFirebaseErrorCode(null))
        assertEquals(FirebaseSignInErrorCode.UNKNOWN, FirebaseAuthErrorMapper.fromFirebaseErrorCode("ERROR_SOMETHING_NEW"))
    }
}
