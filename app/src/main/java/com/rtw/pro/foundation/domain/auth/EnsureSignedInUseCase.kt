package com.rtw.pro.foundation.domain.auth

import com.rtw.pro.foundation.domain.model.UserProfile

sealed class EnsureSignInOutcome {
    data class ProceedHome(val uid: String) : EnsureSignInOutcome()
    data class RequireReLogin(val message: String) : EnsureSignInOutcome()
}

class EnsureSignedInUseCase(
    private val authGateway: AuthGateway,
    private val userProfileGateway: UserProfileGateway,
    private val nowMs: () -> Long = { System.currentTimeMillis() }
) {
    fun execute(defaultDisplayName: String = "Rider"): EnsureSignInOutcome {
        val existing = authGateway.getCurrentSession()
        if (existing != null) {
            return EnsureSignInOutcome.ProceedHome(existing.uid)
        }

        return when (val result = authGateway.signInWithGoogle()) {
            is AuthResult.Success -> {
                userProfileGateway.upsert(
                    UserProfile(
                        uid = result.session.uid,
                        displayName = defaultDisplayName,
                        createdAt = nowMs()
                    )
                )
                EnsureSignInOutcome.ProceedHome(result.session.uid)
            }
            is AuthResult.Failure -> {
                val message = AuthUiMessagePolicy.messageFor(result.error)
                EnsureSignInOutcome.RequireReLogin(message)
            }
        }
    }
}
