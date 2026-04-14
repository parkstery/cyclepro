package com.rtw.pro.ux.domain.analytics

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UxInstrumentationTest {
    @Test
    fun onRoomJoinCompleted_logsElapsedMsAttribute() {
        val logger = FakeLogger()
        val instrumentation = UxInstrumentation(logger, nowMs = { 1000L })

        instrumentation.onRoomJoinCompleted("room-1", 2300L)

        assertEquals(1, logger.events.size)
        val event = logger.events.first()
        assertEquals(UxAnalyticsEventType.ROOM_JOIN_COMPLETED, event.type)
        assertEquals("2300", event.attributes["elapsedMs"])
    }

    @Test
    fun onReminderSet_logsTemplateId() {
        val logger = FakeLogger()
        val instrumentation = UxInstrumentation(logger, nowMs = { 2000L })

        instrumentation.onReminderSet("daily-20h")

        assertTrue(logger.events.first().attributes["templateId"] == "daily-20h")
    }

    private class FakeLogger : UxAnalyticsLogger {
        val events = mutableListOf<UxAnalyticsEvent>()
        override fun log(event: UxAnalyticsEvent) {
            events += event
        }
    }
}
