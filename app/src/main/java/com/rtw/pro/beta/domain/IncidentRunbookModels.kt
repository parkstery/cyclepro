package com.rtw.pro.beta.domain

enum class IncidentPriority {
    P0,
    P1,
    P2
}

data class IncidentTicket(
    val id: String,
    val priority: IncidentPriority,
    val summary: String
)

object IncidentRunbook {
    fun actionFor(priority: IncidentPriority): String {
        return when (priority) {
            IncidentPriority.P0 -> "즉시 핫픽스 및 공지"
            IncidentPriority.P1 -> "주간 패치로 복구"
            IncidentPriority.P2 -> "다음 스프린트 개선 반영"
        }
    }
}
