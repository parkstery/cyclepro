package com.rtw.pro.roomrace.domain.sync

object SyncInterpolator {
    fun interpolate(
        previous: RiderSnapshot,
        current: RiderSnapshot,
        targetTimestampMs: Long
    ): RiderSnapshot {
        if (targetTimestampMs <= previous.serverTimestampMs) return previous
        if (targetTimestampMs >= current.serverTimestampMs) return current

        val t = (targetTimestampMs - previous.serverTimestampMs).toDouble() /
            (current.serverTimestampMs - previous.serverTimestampMs).toDouble()
        val distance = previous.distanceMeters + (current.distanceMeters - previous.distanceMeters) * t
        return RiderSnapshot(
            uid = current.uid,
            distanceMeters = distance,
            serverTimestampMs = targetTimestampMs
        )
    }
}
