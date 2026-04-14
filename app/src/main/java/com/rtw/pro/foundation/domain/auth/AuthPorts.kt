package com.rtw.pro.foundation.domain.auth

import com.rtw.pro.foundation.domain.model.UserProfile

data class AuthSession(
    val uid: String,
    val accessToken: String
)

sealed class AuthError {
    data object Cancelled : AuthError()
    data object TokenExpired : AuthError()
    data class Unknown(val message: String) : AuthError()
}

sealed class AuthResult {
    data class Success(val session: AuthSession) : AuthResult()
    data class Failure(val error: AuthError) : AuthResult()
}

interface AuthGateway {
    fun getCurrentSession(): AuthSession?
    fun signInWithGoogle(): AuthResult
    fun signOut() {}
}

interface UserProfileGateway {
    fun upsert(profile: UserProfile)
}
