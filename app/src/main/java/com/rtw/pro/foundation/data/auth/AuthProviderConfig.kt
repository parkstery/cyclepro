package com.rtw.pro.foundation.data.auth

data class AuthProviderConfig(
    val googleWebClientId: String,
    val firebaseProjectId: String
) {
    fun isValid(): Boolean {
        return googleWebClientId.isNotBlank() && firebaseProjectId.isNotBlank()
    }
}

data class AuthProviderRuntimeState(
    val configValid: Boolean,
    val hasCachedSession: Boolean
)
