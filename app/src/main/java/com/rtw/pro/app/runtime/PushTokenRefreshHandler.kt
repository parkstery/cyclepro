package com.rtw.pro.app.runtime

class PushTokenRefreshHandler(
    private val orchestrator: AppRuntimeOrchestrator
) {
    fun onNewToken(token: String): RuntimeState {
        return orchestrator.onFcmTokenRefreshed(token)
    }
}
