package com.rtw.pro.app.runtime

data class RuntimeState(
    val authReady: Boolean = false,
    val mapReady: Boolean = false,
    val pushTokenSynced: Boolean = false
)

class RuntimeStateStore {
    private var state: RuntimeState = RuntimeState()

    fun get(): RuntimeState = state

    fun updateAuthReady(ready: Boolean) {
        state = state.copy(authReady = ready)
    }

    fun updateMapReady(ready: Boolean) {
        state = state.copy(mapReady = ready)
    }

    fun updatePushTokenSynced(synced: Boolean) {
        state = state.copy(pushTokenSynced = synced)
    }
}
