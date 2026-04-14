package com.rtw.pro.roomrace.domain.sync

data class RaceSnapshot(
    val roomId: String,
    val serverTimestampMs: Long,
    val riders: List<RiderSnapshot>
)

object RejoinRecovery {
    fun recoverForRider(snapshot: RaceSnapshot, uid: String): RiderSnapshot? {
        return snapshot.riders.firstOrNull { it.uid == uid }
    }
}
