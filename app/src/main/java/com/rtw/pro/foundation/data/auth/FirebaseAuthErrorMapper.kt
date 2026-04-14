package com.rtw.pro.foundation.data.auth

object FirebaseAuthErrorMapper {
    /**
     * FirebaseAuthException.errorCode mapping baseline.
     * Real SDK bridge should pass errorCode string values here.
     */
    fun fromFirebaseErrorCode(errorCode: String?): FirebaseSignInErrorCode {
        return when (errorCode?.uppercase()) {
            "ERROR_INVALID_CREDENTIAL",
            "ERROR_INVALID_IDP_RESPONSE" -> FirebaseSignInErrorCode.INVALID_GOOGLE_TOKEN
            "ERROR_NETWORK_REQUEST_FAILED",
            "ERROR_TOO_MANY_REQUESTS" -> FirebaseSignInErrorCode.NETWORK_ERROR
            "ERROR_USER_DISABLED" -> FirebaseSignInErrorCode.USER_DISABLED
            "ERROR_USER_TOKEN_EXPIRED",
            "ERROR_CREDENTIAL_TOO_OLD_LOGIN_AGAIN" -> FirebaseSignInErrorCode.TOKEN_EXPIRED
            else -> FirebaseSignInErrorCode.UNKNOWN
        }
    }
}
