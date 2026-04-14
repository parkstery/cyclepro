package com.rtw.pro.foundation.data.auth

import com.rtw.pro.foundation.domain.auth.AuthGateway

class AuthProviderHealthChecker(
    private val authGateway: AuthGateway,
    private val config: AuthProviderConfig
) {
    fun check(): AuthProviderRuntimeState {
        val session = authGateway.getCurrentSession()
        return AuthProviderRuntimeState(
            configValid = config.isValid(),
            hasCachedSession = session != null
        )
    }
}
