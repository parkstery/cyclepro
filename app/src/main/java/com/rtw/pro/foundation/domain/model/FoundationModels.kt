package com.rtw.pro.foundation.domain.model

enum class RoomStatus {
    WAITING,
    COUNTDOWN,
    RACING,
    FINISHED
}

enum class ParticipantState {
    JOINED,
    READY,
    RACING,
    FINISHED,
    LEFT
}

data class UserProfile(
    val uid: String,
    val displayName: String,
    val photoUrl: String? = null,
    val createdAt: Long
)

data class Room(
    val roomId: String,
    val hostUid: String,
    val routeId: String,
    val startAt: Long,
    val maxRiders: Int,
    val status: RoomStatus
)

data class Participant(
    val uid: String,
    val roomId: String,
    val state: ParticipantState,
    val lastSeenAt: Long
)

data class RaceState(
    val roomId: String,
    val riders: List<String>,
    val ranking: List<String>,
    val updatedAt: Long
)

data class RaceResult(
    val roomId: String,
    val uid: String,
    val elapsedTimeSec: Int,
    val avgSpeedKmh: Double,
    val rank: Int
)
