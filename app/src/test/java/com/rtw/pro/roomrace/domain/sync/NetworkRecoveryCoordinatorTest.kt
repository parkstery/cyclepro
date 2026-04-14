package com.rtw.pro.roomrace.domain.sync

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NetworkRecoveryCoordinatorTest {
    @Test
    fun onDisconnect_predictsOnlyWithinPolicyWindow() {
        val coordinator = NetworkRecoveryCoordinator(PredictionPolicy(maxPredictMs = 2000L))
        val result = coordinator.onDisconnect(
            lastDistanceMeters = 100.0,
            lastSpeedMps = 10.0,
            elapsedMs = 5000L
        )

        assertTrue(result is RecoveryResult.Predicted)
        assertEquals(120.0, result.distanceMeters, 0.0001)
    }

    @Test
    fun onRejoin_recoversSnapshotByUid() {
        val coordinator = NetworkRecoveryCoordinator()
        val snapshot = RaceSnapshot(
            roomId = "room1",
            serverTimestampMs = 1000L,
            riders = listOf(
                RiderSnapshot(uid = "u1", distanceMeters = 50.0, serverTimestampMs = 900L),
                RiderSnapshot(uid = "u2", distanceMeters = 70.0, serverTimestampMs = 900L)
            )
        )
        val result = coordinator.onRejoin(snapshot, "u2")

        assertTrue(result is RecoveryResult.Recovered)
        assertEquals("u2", result.snapshot.uid)
        assertEquals(70.0, result.snapshot.distanceMeters, 0.0001)
    }
}
