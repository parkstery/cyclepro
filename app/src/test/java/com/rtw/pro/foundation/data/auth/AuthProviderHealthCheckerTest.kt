package com.rtw.pro.foundation.data.auth

import com.rtw.pro.foundation.domain.auth.AuthGateway
import com.rtw.pro.foundation.domain.auth.AuthResult
import com.rtw.pro.foundation.domain.auth.AuthSession
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AuthProviderHealthCheckerTest {
    @Test
    fun check_returnsValid_whenConfigAndSessionExist() {
        val checker = AuthProviderHealthChecker(
            authGateway = object : AuthGateway {
                override fun getCurrentSession(): AuthSession? = AuthSession("u1", "t1")
                override fun signInWithGoogle(): AuthResult = AuthResult.Success(AuthSession("u1", "t1"))
            },
            config = AuthProviderConfig(
                googleWebClientId = "web-client-id",
                firebaseProjectId = "rtw-project"
            )
        )

        val state = checker.check()
        assertTrue(state.configValid)
        assertTrue(state.hasCachedSession)
    }

    @Test
    fun check_returnsInvalid_whenConfigContainsTodoPlaceholders() {
        val checker = AuthProviderHealthChecker(
            authGateway = object : AuthGateway {
                override fun getCurrentSession(): AuthSession? = null
                override fun signInWithGoogle(): AuthResult = AuthResult.Success(AuthSession("u1", "t1"))
            },
            config = AuthProviderConfig(
                googleWebClientId = "TODO_WEB_CLIENT_ID",
                firebaseProjectId = "TODO_FIREBASE_PROJECT_ID"
            )
        )

        val state = checker.check()
        assertFalse(state.configValid)
    }
}
