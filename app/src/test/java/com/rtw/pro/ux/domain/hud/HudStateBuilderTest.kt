package com.rtw.pro.ux.domain.hud

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class HudStateBuilderTest {
    @Test
    fun build_hidesAux_whenCollapsed() {
        val state = HudStateBuilder.build(
            core = HudCoreData(25.0, 2, 8.0, 6.0, 1200.0),
            aux = HudAuxData(24.0, 35.0, "stable"),
            expandAux = false
        )
        assertNull(state.aux)
        assertEquals(false, state.isAuxExpanded)
    }
}
