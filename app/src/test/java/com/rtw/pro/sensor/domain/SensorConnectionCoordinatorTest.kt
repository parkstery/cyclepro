package com.rtw.pro.sensor.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class SensorConnectionCoordinatorTest {
    @Test
    fun connect_switchesFallback_whenUnsupportedDevice() {
        val gateway = FakeGateway()
        val coordinator = SensorConnectionCoordinator(gateway)
        val state = coordinator.connect(SensorDevice("x", "unsupported", isSupported = false))
        assertEquals(SensorConnectionState.FALLBACK_VIRTUAL, state)
    }

    @Test
    fun handleDisconnect_reconnectsWithinAttempts() {
        val gateway = FakeGateway(connectSequence = mutableListOf(false, true))
        val coordinator = SensorConnectionCoordinator(gateway, ReconnectPolicy(maxAttempts = 3))
        val state = coordinator.handleDisconnect("d1")
        assertEquals(SensorConnectionState.CONNECTED, state)
    }

    private class FakeGateway(
        private val connectSequence: MutableList<Boolean> = mutableListOf(true)
    ) : SensorGateway {
        override fun scan(): List<SensorDevice> = emptyList()
        override fun connect(deviceId: String): Boolean {
            return if (connectSequence.isNotEmpty()) connectSequence.removeAt(0) else false
        }
        override fun disconnect() = Unit
    }
}
