package com.rtw.pro.foundation.data.auth

data class AuthProviderConfig(
    val googleWebClientId: String,
    val firebaseProjectId: String
) {
    fun isValid(): Boolean {
        return googleWebClientId.isConfiguredValue() && firebaseProjectId.isConfiguredValue()
    }

    private fun String.isConfiguredValue(): Boolean {
        val normalized = trim()
        if (normalized.isBlank()) return false
        return !normalized.uppercase().contains("TODO")
    }
}

data class AuthProviderRuntimeState(
    val configValid: Boolean,
    val hasCachedSession: Boolean
)
