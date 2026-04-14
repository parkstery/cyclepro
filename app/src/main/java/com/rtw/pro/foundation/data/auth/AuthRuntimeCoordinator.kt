package com.rtw.pro.foundation.data.auth

import com.rtw.pro.foundation.domain.auth.AuthGateway
import com.rtw.pro.foundation.domain.auth.AuthResult

enum class AuthRuntimeStatus {
    READY_WITH_SESSION,
    READY_AFTER_SIGN_IN,
    BLOCKED_BY_INVALID_CONFIG,
    FAILED_SIGN_IN
}

data class AuthRuntimeResult(
    val status: AuthRuntimeStatus,
    val message: String
)

class AuthRuntimeCoordinator(
    private val config: AuthProviderConfig,
    private val authGateway: AuthGateway
) {
    fun initialize(): AuthRuntimeResult {
        if (!config.isValid()) {
            return AuthRuntimeResult(
                status = AuthRuntimeStatus.BLOCKED_BY_INVALID_CONFIG,
                message = "Auth provider config is invalid"
            )
        }
        val session = authGateway.getCurrentSession()
        if (session != null) {
            return AuthRuntimeResult(
                status = AuthRuntimeStatus.READY_WITH_SESSION,
                message = "Auth ready with cached session"
            )
        }
        return when (authGateway.signInWithGoogle()) {
            is AuthResult.Success -> AuthRuntimeResult(
                status = AuthRuntimeStatus.READY_AFTER_SIGN_IN,
                message = "Auth ready after Google sign-in"
            )
            is AuthResult.Failure -> AuthRuntimeResult(
                status = AuthRuntimeStatus.FAILED_SIGN_IN,
                message = "Auth sign-in failed"
            )
        }
    }
}
