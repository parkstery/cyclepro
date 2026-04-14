package com.rtw.pro.foundation.data.auth

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
    private var lastFirebaseErrorCode: String? = null

    override fun currentUid(): String? {
        // TODO: Return FirebaseAuth.getInstance().currentUser?.uid
        return null
    }

    override fun currentAccessToken(): String? {
        // TODO: Read current user token from Firebase SDK.
        return null
    }

    override fun signInWithGoogleIdToken(idToken: String): Boolean {
        // TODO: Exchange Google ID token with Firebase credential sign-in.
        // On failure, assign FirebaseAuthException.errorCode to lastFirebaseErrorCode.
        lastFirebaseErrorCode = "ERROR_INVALID_CREDENTIAL"
        return false
    }

    override fun signInWithGoogleIdTokenResult(idToken: String): FirebaseSignInResult {
        // TODO: Replace stub error assignment with real FirebaseAuthException.errorCode value.
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
        // TODO: Sign out from Firebase session.
        lastFirebaseErrorCode = null
    }
}
