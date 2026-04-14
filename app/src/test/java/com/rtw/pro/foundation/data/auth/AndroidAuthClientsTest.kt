package com.rtw.pro.foundation.data.auth

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AndroidAuthClientsTest {
    @Test
    fun androidGoogleSignInClient_returnsTokenFromBridge() {
        val client = AndroidGoogleSignInClient(
            bridge = object : AndroidGoogleSignInBridge {
                override fun requestIdTokenInteractive(): String? = "id-token"
                override fun signOut() = Unit
            }
        )
        assertEquals("id-token", client.requestIdToken())
    }

    @Test
    fun androidFirebaseAuthClient_delegatesBridgeCalls() {
        val client = AndroidFirebaseAuthClient(
            bridge = object : AndroidFirebaseAuthBridge {
                override fun currentUid(): String? = "uid-1"
                override fun currentAccessToken(): String? = "token-1"
                override fun signInWithGoogleIdToken(idToken: String): Boolean = idToken == "ok"
                override fun signInWithGoogleIdTokenResult(idToken: String): FirebaseSignInResult {
                    return if (idToken == "ok") {
                        FirebaseSignInResult(success = true)
                    } else {
                        FirebaseSignInResult(success = false, errorCode = FirebaseSignInErrorCode.INVALID_GOOGLE_TOKEN)
                    }
                }
                override fun signOut() = Unit
            }
        )
        assertEquals("uid-1", client.currentUserUid())
        assertEquals("token-1", client.currentAccessToken())
        assertTrue(client.signInWithGoogleIdToken("ok"))
        assertTrue(client.signInWithGoogleIdTokenDetailed("ok").success)
    }
}
