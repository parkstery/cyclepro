package com.rtw.pro.foundation.data.auth

import com.rtw.pro.foundation.domain.auth.AuthGateway
import com.rtw.pro.foundation.domain.auth.AuthResult
import com.rtw.pro.foundation.domain.auth.AuthUiMessagePolicy

enum class AuthRuntimeStatus {
    READY_WITH_SESSION,
    READY_AFTER_SIGN_IN,
    BLOCKED_BY_INVALID_CONFIG,
    FAILED_SIGN_IN,
    SIGNED_OUT
}

data class AuthRuntimeResult(
    val status: AuthRuntimeStatus,
    val message: String
)

class AuthRuntimeCoordinator(
    private val config: AuthProviderConfig,
    private val authGateway: AuthGateway
) {
    private fun failureResult(error: com.rtw.pro.foundation.domain.auth.AuthError): AuthRuntimeResult {
        return AuthRuntimeResult(
            status = AuthRuntimeStatus.FAILED_SIGN_IN,
            message = AuthUiMessagePolicy.messageFor(error)
        )
    }

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
        return AuthRuntimeResult(
            status = AuthRuntimeStatus.SIGNED_OUT,
            message = "No cached auth session. Sign in is required."
        )
    }

    fun retrySignIn(): AuthRuntimeResult {
        if (!config.isValid()) {
            return AuthRuntimeResult(
                status = AuthRuntimeStatus.BLOCKED_BY_INVALID_CONFIG,
                message = "Auth provider config is invalid"
            )
        }
        return when (val signIn = authGateway.signInWithGoogle()) {
            is AuthResult.Success -> AuthRuntimeResult(
                status = AuthRuntimeStatus.READY_AFTER_SIGN_IN,
                message = "Auth ready after retry sign-in"
            )
            is AuthResult.Failure -> failureResult(signIn.error)
        }
    }

    fun signOut(): AuthRuntimeResult {
        authGateway.signOut()
        return AuthRuntimeResult(
            status = AuthRuntimeStatus.SIGNED_OUT,
            message = "Auth signed out"
        )
    }
}
