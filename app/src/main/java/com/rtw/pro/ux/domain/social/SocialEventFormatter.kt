package com.rtw.pro.ux.domain.social

object SocialEventFormatter {
    fun format(type: SocialEventType, actor: String? = null): String {
        return when (type) {
            SocialEventType.JOIN -> "${actor ?: "라이더"} 입장"
            SocialEventType.LEAVE -> "${actor ?: "라이더"} 퇴장"
            SocialEventType.COUNTDOWN -> "카운트다운 시작"
            SocialEventType.START -> "레이스 출발"
            SocialEventType.OVERTAKE -> "${actor ?: "라이더"} 추월"
            SocialEventType.OVERTAKEN -> "${actor ?: "라이더"}에게 추월됨"
            SocialEventType.QUICK_REACTION -> "${actor ?: "라이더"} 응원"
        }
    }
}
