package com.rtw.pro.roomrace.domain.result

import com.rtw.pro.roomrace.domain.sync.RiderSnapshot

data class RiderFinish(
    val uid: String,
    val distanceMeters: Double,
    val finishTimestampMs: Long?,
    val elapsedTimeSec: Int,
    val avgSpeedKmh: Double,
    val dnf: Boolean
)

data class RankedRiderResult(
    val uid: String,
    val rank: Int,
    val elapsedTimeSec: Int,
    val avgSpeedKmh: Double,
    val gapToLeaderMeters: Double,
    val dnf: Boolean
)

object RaceResultCalculator {
    fun rank(finishes: List<RiderFinish>): List<RankedRiderResult> {
        if (finishes.isEmpty()) return emptyList()
        val ordered = finishes.sortedWith(
            compareBy<RiderFinish> { it.dnf }
                .thenByDescending { it.distanceMeters }
                .thenBy { it.finishTimestampMs ?: Long.MAX_VALUE }
        )
        val leaderDistance = ordered.first().distanceMeters
        return ordered.mapIndexed { index, rider ->
            RankedRiderResult(
                uid = rider.uid,
                rank = index + 1,
                elapsedTimeSec = rider.elapsedTimeSec,
                avgSpeedKmh = rider.avgSpeedKmh,
                gapToLeaderMeters = leaderDistance - rider.distanceMeters,
                dnf = rider.dnf
            )
        }
    }

    fun toFinish(snapshot: RiderSnapshot, elapsedTimeSec: Int, avgSpeedKmh: Double, dnf: Boolean): RiderFinish {
        return RiderFinish(
            uid = snapshot.uid,
            distanceMeters = snapshot.distanceMeters,
            finishTimestampMs = if (dnf) null else snapshot.serverTimestampMs,
            elapsedTimeSec = elapsedTimeSec,
            avgSpeedKmh = avgSpeedKmh,
            dnf = dnf
        )
    }
}
