package com.rtw.pro.foundation.data.auth

import com.rtw.pro.foundation.domain.auth.AuthResult
import kotlin.test.Test
import kotlin.test.assertTrue

class FirebaseAuthGatewayErrorMappingTest {
    @Test
    fun signInWithGoogle_mapsTokenExpiredError() {
        val gateway = FirebaseAuthGateway(
            firebaseAuthClient = object : FirebaseAuthClient {
                override fun currentUserUid(): String? = null
                override fun currentAccessToken(): String? = null
                override fun signInWithGoogleIdToken(idToken: String): Boolean = false
                override fun signInWithGoogleIdTokenDetailed(idToken: String): FirebaseSignInResult {
                    return FirebaseSignInResult(success = false, errorCode = FirebaseSignInErrorCode.TOKEN_EXPIRED)
                }
            },
            googleSignInClient = object : GoogleSignInClient {
                override fun requestIdToken(): String? = "id-token"
            }
        )

        val result = gateway.signInWithGoogle()
        assertTrue(result is AuthResult.Failure)
    }

    @Test
    fun signInWithGoogle_mapsNetworkErrorToUnknownNetworkMessage() {
        val gateway = FirebaseAuthGateway(
            firebaseAuthClient = object : FirebaseAuthClient {
                override fun currentUserUid(): String? = null
                override fun currentAccessToken(): String? = null
                override fun signInWithGoogleIdToken(idToken: String): Boolean = false
                override fun signInWithGoogleIdTokenDetailed(idToken: String): FirebaseSignInResult {
                    return FirebaseSignInResult(success = false, errorCode = FirebaseSignInErrorCode.NETWORK_ERROR)
                }
            },
            googleSignInClient = object : GoogleSignInClient {
                override fun requestIdToken(): String? = "id-token"
            }
        )
        val result = gateway.signInWithGoogle()
        assertTrue(result is AuthResult.Failure)
    }
}
