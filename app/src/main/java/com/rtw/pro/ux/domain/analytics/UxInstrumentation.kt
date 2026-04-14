package com.rtw.pro.ux.domain.analytics

class UxInstrumentation(
    private val logger: UxAnalyticsLogger,
    private val nowMs: () -> Long = { System.currentTimeMillis() }
) {
    fun onRoomJoinStarted(roomId: String) {
        logger.log(
            UxAnalyticsEvent(
                type = UxAnalyticsEventType.ROOM_JOIN_STARTED,
                timestampMs = nowMs(),
                attributes = mapOf("roomId" to roomId)
            )
        )
    }

    fun onRoomJoinCompleted(roomId: String, elapsedMs: Long) {
        logger.log(
            UxAnalyticsEvent(
                type = UxAnalyticsEventType.ROOM_JOIN_COMPLETED,
                timestampMs = nowMs(),
                attributes = mapOf("roomId" to roomId, "elapsedMs" to elapsedMs.toString())
            )
        )
    }

    fun onSocialEventShown(eventType: String) {
        logger.log(
            UxAnalyticsEvent(
                type = UxAnalyticsEventType.SOCIAL_EVENT_SHOWN,
                timestampMs = nowMs(),
                attributes = mapOf("eventType" to eventType)
            )
        )
    }

    fun onPostRaceSummaryViewed(cardCount: Int) {
        logger.log(
            UxAnalyticsEvent(
                type = UxAnalyticsEventType.POST_RACE_SUMMARY_VIEWED,
                timestampMs = nowMs(),
                attributes = mapOf("cardCount" to cardCount.toString())
            )
        )
    }

    fun onRetryTapped() {
        logger.log(
            UxAnalyticsEvent(
                type = UxAnalyticsEventType.POST_RACE_RETRY_TAPPED,
                timestampMs = nowMs()
            )
        )
    }

    fun onReminderSet(templateId: String) {
        logger.log(
            UxAnalyticsEvent(
                type = UxAnalyticsEventType.POST_RACE_REMINDER_SET,
                timestampMs = nowMs(),
                attributes = mapOf("templateId" to templateId)
            )
        )
    }
}
