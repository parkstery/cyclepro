package com.rtw.pro.notification.data

enum class FcmErrorCode {
    TOKEN_UNAVAILABLE,
    NETWORK_ERROR,
    UNAUTHORIZED,
    INVALID_TOKEN,
    UNKNOWN
}

object FcmErrorMapper {
    fun fromHttpStatus(statusCode: Int?): FcmErrorCode {
        return when (statusCode) {
            401, 403 -> FcmErrorCode.UNAUTHORIZED
            400 -> FcmErrorCode.INVALID_TOKEN
            408, 429, 500, 502, 503, 504 -> FcmErrorCode.NETWORK_ERROR
            null -> FcmErrorCode.UNKNOWN
            else -> FcmErrorCode.UNKNOWN
        }
    }
}
