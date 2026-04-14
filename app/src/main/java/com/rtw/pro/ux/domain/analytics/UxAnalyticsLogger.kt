package com.rtw.pro.ux.domain.analytics

interface UxAnalyticsLogger {
    fun log(event: UxAnalyticsEvent)
}
