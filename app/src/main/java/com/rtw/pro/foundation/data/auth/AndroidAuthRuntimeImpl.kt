package com.rtw.pro.foundation.data.auth

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import java.util.concurrent.TimeUnit

/**
 * Runtime-oriented implementation skeleton.
 * SDK binding should be added in TODO blocks.
 */
class AndroidGoogleSignInBridgeImpl : AndroidGoogleSignInBridge {
    /**
     * Integration point for ActivityResult callback:
     * - launchSignIn(): called before external intent.
     * - onSignInResultToken()/onSignInResultError(): called from Activity result handler.
     */
    private var pendingToken: String? = null
    private var pendingError: GoogleSignInFailureCode? = null

    fun launchSignIn() {
        pendingToken = null
        pendingError = null
        // TODO: Launch Google sign-in intent using ActivityResultLauncher.
    }

    fun onSignInResultToken(idToken: String?) {
        pendingToken = idToken
        if (idToken.isNullOrBlank()) pendingError = GoogleSignInFailureCode.UNKNOWN
    }

    fun onSignInResultError(code: GoogleSignInFailureCode) {
        pendingToken = null
        pendingError = code
    }

    fun onSignInResultErrorStatus(statusCode: Int?) {
        onSignInResultError(GoogleSignInErrorMapper.fromStatusCode(statusCode))
    }

    override fun requestIdTokenInteractive(): String? {
        // TODO: Replace with synchronous wrapper over ActivityResult flow.
        return pendingToken
    }

    override fun requestIdTokenInteractiveResult(): GoogleSignInRequestResult {
        return if (!pendingToken.isNullOrBlank()) {
            GoogleSignInRequestResult(idToken = pendingToken)
        } else {
            GoogleSignInRequestResult(idToken = null, failureCode = pendingError ?: GoogleSignInFailureCode.UNKNOWN)
        }
    }

    override fun signOut() {
        // TODO: Call Google sign-out API and revoke token if needed.
        pendingToken = null
        pendingError = null
    }
}

class AndroidFirebaseAuthBridgeImpl : AndroidFirebaseAuthBridge {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var lastFirebaseErrorCode: String? = null

    override fun currentUid(): String? {
        return auth.currentUser?.uid
    }

    override fun currentAccessToken(): String? {
        val user = auth.currentUser ?: return null
        return try {
            val tokenTask = user.getIdToken(false)
            Tasks.await(tokenTask, 5, TimeUnit.SECONDS)?.token
        } catch (_: Exception) {
            null
        }
    }

    override fun signInWithGoogleIdToken(idToken: String): Boolean {
        if (idToken.isBlank()) {
            lastFirebaseErrorCode = "ERROR_INVALID_CREDENTIAL"
            return false
        }
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val task = auth.signInWithCredential(credential)
            Tasks.await(task, 10, TimeUnit.SECONDS)
            lastFirebaseErrorCode = null
            task.isSuccessful
        } catch (e: Exception) {
            lastFirebaseErrorCode = extractFirebaseErrorCode(e)
            false
        }
    }

    override fun signInWithGoogleIdTokenResult(idToken: String): FirebaseSignInResult {
        val success = signInWithGoogleIdToken(idToken)
        return if (success) {
            FirebaseSignInResult(success = true)
        } else {
            FirebaseSignInResult(
                success = false,
                errorCode = FirebaseAuthErrorMapper.fromFirebaseErrorCode(lastFirebaseErrorCode)
            )
        }
    }

    override fun signOut() {
        auth.signOut()
        lastFirebaseErrorCode = null
    }

    private fun extractFirebaseErrorCode(exception: Exception): String {
        if (exception is FirebaseAuthException && !exception.errorCode.isNullOrBlank()) {
            return exception.errorCode
        }
        val cause = exception.cause
        if (cause is FirebaseAuthException && !cause.errorCode.isNullOrBlank()) {
            return cause.errorCode
        }
        return "ERROR_UNKNOWN"
    }
}
