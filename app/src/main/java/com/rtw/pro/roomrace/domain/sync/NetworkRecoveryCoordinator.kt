package com.rtw.pro.roomrace.domain.sync

sealed class RecoveryResult {
    data class Predicted(val distanceMeters: Double) : RecoveryResult()
    data class Recovered(val snapshot: RiderSnapshot) : RecoveryResult()
    data object NotFound : RecoveryResult()
}

class NetworkRecoveryCoordinator(
    private val predictionPolicy: PredictionPolicy = PredictionPolicy()
) {
    fun onDisconnect(
        lastDistanceMeters: Double,
        lastSpeedMps: Double,
        elapsedMs: Long
    ): RecoveryResult {
        return RecoveryResult.Predicted(
            distanceMeters = DisconnectPrediction.predictDistance(
                lastDistanceMeters = lastDistanceMeters,
                lastSpeedMps = lastSpeedMps,
                disconnectElapsedMs = elapsedMs,
                policy = predictionPolicy
            )
        )
    }

    fun onRejoin(snapshot: RaceSnapshot, uid: String): RecoveryResult {
        val recovered = RejoinRecovery.recoverForRider(snapshot, uid) ?: return RecoveryResult.NotFound
        return RecoveryResult.Recovered(recovered)
    }
}
