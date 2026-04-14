package com.rtw.pro.notification.data

data class FcmTokenSyncResult(
    val success: Boolean,
    val token: String?,
    val errorCode: FcmErrorCode? = null
)

interface FcmTokenProvider {
    fun currentToken(): String?
}

interface FcmTokenRegistrar {
    fun register(token: String): Boolean
    fun registerDetailed(token: String): FcmTokenSyncResult {
        val ok = register(token)
        return FcmTokenSyncResult(success = ok, token = token, errorCode = if (ok) null else FcmErrorCode.UNKNOWN)
    }
}

class FcmTokenSyncCoordinator(
    private val provider: FcmTokenProvider,
    private val registrar: FcmTokenRegistrar
) {
    fun syncCurrentToken(): FcmTokenSyncResult {
        val token = provider.currentToken() ?: return FcmTokenSyncResult(false, null, FcmErrorCode.TOKEN_UNAVAILABLE)
        return registrar.registerDetailed(token)
    }

    fun onTokenRefreshed(newToken: String): FcmTokenSyncResult {
        return registrar.registerDetailed(newToken)
    }
}
