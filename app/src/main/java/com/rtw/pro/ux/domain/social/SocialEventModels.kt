package com.rtw.pro.ux.domain.social

enum class SocialEventType {
    JOIN,
    LEAVE,
    COUNTDOWN,
    START,
    OVERTAKE,
    OVERTAKEN,
    QUICK_REACTION
}

data class SocialEvent(
    val type: SocialEventType,
    val message: String,
    val timestampMs: Long
)

data class SocialFeedbackOptions(
    val vibrationEnabled: Boolean = true,
    val soundEnabled: Boolean = false
)
