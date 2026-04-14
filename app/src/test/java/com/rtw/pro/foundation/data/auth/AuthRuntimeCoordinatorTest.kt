package com.rtw.pro.foundation.data.auth

import com.rtw.pro.foundation.domain.auth.AuthGateway
import com.rtw.pro.foundation.domain.auth.AuthResult
import com.rtw.pro.foundation.domain.auth.AuthSession
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthRuntimeCoordinatorTest {
    @Test
    fun initialize_returnsBlocked_whenConfigInvalid() {
        val coordinator = AuthRuntimeCoordinator(
            config = AuthProviderConfig("", "project"),
            authGateway = fakeGateway()
        )
        val result = coordinator.initialize()
        assertEquals(AuthRuntimeStatus.BLOCKED_BY_INVALID_CONFIG, result.status)
    }

    @Test
    fun initialize_returnsReadyWithSession_whenCachedSessionExists() {
        val coordinator = AuthRuntimeCoordinator(
            config = AuthProviderConfig("web", "project"),
            authGateway = fakeGateway(session = AuthSession("u1", "t1"))
        )
        val result = coordinator.initialize()
        assertEquals(AuthRuntimeStatus.READY_WITH_SESSION, result.status)
    }

    @Test
    fun initialize_returnsBlocked_whenConfigContainsTodoPlaceholders() {
        val coordinator = AuthRuntimeCoordinator(
            config = AuthProviderConfig("TODO_WEB_CLIENT_ID", "TODO_FIREBASE_PROJECT_ID"),
            authGateway = fakeGateway(session = AuthSession("u1", "t1"))
        )
        val result = coordinator.initialize()
        assertEquals(AuthRuntimeStatus.BLOCKED_BY_INVALID_CONFIG, result.status)
    }

    private fun fakeGateway(
        session: AuthSession? = null
    ): AuthGateway = object : AuthGateway {
        override fun getCurrentSession(): AuthSession? = session
        override fun signInWithGoogle(): AuthResult = AuthResult.Success(AuthSession("u2", "t2"))
    }
}
