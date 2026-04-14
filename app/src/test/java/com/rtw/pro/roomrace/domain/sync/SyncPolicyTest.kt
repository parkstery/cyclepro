package com.rtw.pro.roomrace.domain.sync

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SyncPolicyTest {
    @Test
    fun validatesUploadHzRange() {
        val policy = SyncPolicy()
        assertTrue(policy.isUploadHzValid(1.0))
        assertTrue(policy.isUploadHzValid(2.0))
        assertFalse(policy.isUploadHzValid(0.5))
    }

    @Test
    fun detectsStaleByThreshold() {
        val policy = SyncPolicy(staleThresholdMs = 3000L)
        assertTrue(policy.isStale(lastReceivedAtMs = 1000L, nowMs = 4000L))
        assertFalse(policy.isStale(lastReceivedAtMs = 2000L, nowMs = 3999L))
    }
}
