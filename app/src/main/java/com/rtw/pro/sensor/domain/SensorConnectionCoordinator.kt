package com.rtw.pro.sensor.domain

class SensorConnectionCoordinator(
    private val gateway: SensorGateway,
    private val reconnectPolicy: ReconnectPolicy = ReconnectPolicy()
) {
    var state: SensorConnectionState = SensorConnectionState.IDLE
        private set

    fun startScan(): List<SensorDevice> {
        state = SensorConnectionState.SCANNING
        return gateway.scan()
    }

    fun connect(device: SensorDevice): SensorConnectionState {
        if (!device.isSupported) {
            state = SensorConnectionState.FALLBACK_VIRTUAL
            return state
        }
        state = SensorConnectionState.CONNECTING
        state = if (gateway.connect(device.id)) {
            SensorConnectionState.CONNECTED
        } else {
            SensorConnectionState.FALLBACK_VIRTUAL
        }
        return state
    }

    fun handleDisconnect(deviceId: String): SensorConnectionState {
        var attempt = 0
        while (reconnectPolicy.canRetry(attempt)) {
            state = SensorConnectionState.RECONNECTING
            val ok = gateway.connect(deviceId)
            if (ok) {
                state = SensorConnectionState.CONNECTED
                return state
            }
            attempt += 1
        }
        state = SensorConnectionState.FALLBACK_VIRTUAL
        return state
    }
}
