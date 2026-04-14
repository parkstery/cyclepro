package com.rtw.pro.ux.domain.social

import kotlin.test.Test
import kotlin.test.assertTrue

class SocialEventFormatterTest {
    @Test
    fun format_returnsShortReadableMessage() {
        val msg = SocialEventFormatter.format(SocialEventType.OVERTAKE, actor = "Alex")
        assertTrue(msg.contains("추월"))
    }
}
