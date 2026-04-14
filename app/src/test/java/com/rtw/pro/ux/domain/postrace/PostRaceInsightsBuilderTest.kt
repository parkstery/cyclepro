package com.rtw.pro.ux.domain.postrace

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PostRaceInsightsBuilderTest {
    @Test
    fun build_createsThreeSummaryCards_andActionOptions() {
        val insights = PostRaceInsightsBuilder.build(
            PostRaceInput(
                overtakeCount = 4,
                paceDeviationPercent = 4.2,
                finalSegmentGapMeters = 12.0,
                pbDeltaSeconds = -8
            )
        )
        assertEquals(3, insights.summaryCards.size)
        assertTrue(insights.actions.canRetryNow)
        assertTrue(insights.actions.canSetEventReminder)
    }
}
