package com.rtw.pro.ux.domain.postrace

enum class InsightLabel {
    GOOD,
    NORMAL,
    NEEDS_IMPROVEMENT
}

data class InsightCard(
    val title: String,
    val value: String,
    val label: InsightLabel
)

data class PostRaceActions(
    val canRetryNow: Boolean,
    val canSetEventReminder: Boolean
)

data class PostRaceInsights(
    val summaryCards: List<InsightCard>,
    val actions: PostRaceActions
)
