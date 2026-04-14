package com.rtw.pro.app.runtime

interface AppLifecycleCallbacks {
    fun onAppStarted()
    fun onTokenRefreshed(newToken: String)
}
