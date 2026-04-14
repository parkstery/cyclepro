package com.rtw.pro.beta.domain

import kotlin.test.Test
import kotlin.test.assertTrue

class IncidentRunbookModelsTest {
    @Test
    fun actionFor_returnsExpectedMessage() {
        val action = IncidentRunbook.actionFor(IncidentPriority.P0)
        assertTrue(action.contains("핫픽스"))
    }
}
