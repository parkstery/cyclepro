package com.rtw.pro.foundation.domain.auth

import com.rtw.pro.foundation.domain.model.UserProfile
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EnsureSignedInUseCaseTest {
    @Test
    fun execute_proceedsHome_whenSessionExists() {
        val auth = FakeAuthGateway(current = AuthSession("u1", "t1"))
        val profile = FakeUserProfileGateway()
        val useCase = EnsureSignedInUseCase(auth, profile)

        val result = useCase.execute()
        assertTrue(result is EnsureSignInOutcome.ProceedHome)
        assertTrue(profile.upserted.isEmpty())
    }

    @Test
    fun execute_upsertsProfile_whenGoogleSignInSuccess() {
        val auth = FakeAuthGateway(
            current = null,
            signInResult = AuthResult.Success(AuthSession("u2", "t2"))
        )
        val profile = FakeUserProfileGateway()
        val useCase = EnsureSignedInUseCase(auth, profile, nowMs = { 1000L })

        val result = useCase.execute(defaultDisplayName = "PM")
        assertTrue(result is EnsureSignInOutcome.ProceedHome)
        assertEquals(1, profile.upserted.size)
        assertEquals("u2", profile.upserted.first().uid)
    }

    @Test
    fun execute_requiresRelogin_whenTokenExpired() {
        val auth = FakeAuthGateway(
            current = null,
            signInResult = AuthResult.Failure(AuthError.TokenExpired)
        )
        val profile = FakeUserProfileGateway()
        val useCase = EnsureSignedInUseCase(auth, profile)

        val result = useCase.execute()
        assertTrue(result is EnsureSignInOutcome.RequireReLogin)
        assertTrue(result.message.contains("만료"))
    }

    private class FakeAuthGateway(
        private val current: AuthSession? = null,
        private val signInResult: AuthResult = AuthResult.Failure(AuthError.Cancelled)
    ) : AuthGateway {
        override fun getCurrentSession(): AuthSession? = current
        override fun signInWithGoogle(): AuthResult = signInResult
    }

    private class FakeUserProfileGateway : UserProfileGateway {
        val upserted = mutableListOf<UserProfile>()
        override fun upsert(profile: UserProfile) {
            upserted += profile
        }
    }
}
