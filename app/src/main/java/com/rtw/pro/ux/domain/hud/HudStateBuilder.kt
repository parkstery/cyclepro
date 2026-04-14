package com.rtw.pro.ux.domain.hud

object HudStateBuilder {
    fun build(
        core: HudCoreData,
        aux: HudAuxData?,
        expandAux: Boolean
    ): HudUiState {
        return HudUiState(
            core = core,
            aux = if (expandAux) aux else null,
            isAuxExpanded = expandAux
        )
    }
}
