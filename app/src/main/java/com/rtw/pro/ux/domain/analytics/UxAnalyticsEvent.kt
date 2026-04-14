package com.rtw.pro.ux.domain.analytics

enum class UxAnalyticsEventType {
    ROOM_JOIN_STARTED,
    ROOM_JOIN_COMPLETED,
    COUNTDOWN_STARTED,
    RIDE_COMPLETED,
    HUD_CORE_VIEWED,
    HUD_AUX_EXPANDED,
    SOCIAL_EVENT_SHOWN,
    POST_RACE_SUMMARY_VIEWED,
    POST_RACE_DRILLDOWN_OPENED,
    POST_RACE_RETRY_TAPPED,
    POST_RACE_REMINDER_SET
}

data class UxAnalyticsEvent(
    val type: UxAnalyticsEventType,
    val timestampMs: Long,
    val attributes: Map<String, String> = emptyMap()
)
