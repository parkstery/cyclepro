package com.rtw.pro.foundation.data.auth

import com.rtw.pro.foundation.domain.auth.AuthResult
import kotlin.test.Test
import kotlin.test.assertTrue

class FirebaseAuthGatewayTest {
    @Test
    fun signInWithGoogle_returnsSuccess_whenClientsSucceed() {
        val gateway = FirebaseAuthGateway(
            firebaseAuthClient = object : FirebaseAuthClient {
                override fun currentUserUid(): String? = "uid-1"
                override fun currentAccessToken(): String? = "token-1"
                override fun signInWithGoogleIdToken(idToken: String): Boolean = true
            },
            googleSignInClient = object : GoogleSignInClient {
                override fun requestIdToken(): String? = "google-id-token"
            }
        )
        val result = gateway.signInWithGoogle()
        assertTrue(result is AuthResult.Success)
    }
}
