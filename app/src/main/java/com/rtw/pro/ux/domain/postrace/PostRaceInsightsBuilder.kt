package com.rtw.pro.ux.domain.postrace

data class PostRaceInput(
    val overtakeCount: Int,
    val paceDeviationPercent: Double,
    val finalSegmentGapMeters: Double,
    val pbDeltaSeconds: Int
)

object PostRaceInsightsBuilder {
    fun build(input: PostRaceInput): PostRaceInsights {
        val cards = listOf(
            InsightCard(
                title = "추월 타이밍",
                value = "${input.overtakeCount}회",
                label = if (input.overtakeCount >= 3) InsightLabel.GOOD else InsightLabel.NORMAL
            ),
            InsightCard(
                title = "구간 페이스 편차",
                value = "${"%.1f".format(input.paceDeviationPercent)}%",
                label = when {
                    input.paceDeviationPercent <= 5.0 -> InsightLabel.GOOD
                    input.paceDeviationPercent <= 10.0 -> InsightLabel.NORMAL
                    else -> InsightLabel.NEEDS_IMPROVEMENT
                }
            ),
            InsightCard(
                title = "마지막 20% 격차",
                value = "${"%.1f".format(input.finalSegmentGapMeters)}m",
                label = if (input.finalSegmentGapMeters <= 20.0) InsightLabel.GOOD else InsightLabel.NEEDS_IMPROVEMENT
            )
        )

        return PostRaceInsights(
            summaryCards = cards.take(3),
            actions = PostRaceActions(
                canRetryNow = true,
                canSetEventReminder = true
            )
        )
    }
}
