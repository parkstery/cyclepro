package com.rtw.pro.baseline.domain

/**
 * Step 1 state transition guard:
 * IDLE -> RUNNING -> FINISHED.
 */
class RideSessionStateMachine(
    initialState: RideSessionState = RideSessionState.IDLE
) {
    var state: RideSessionState = initialState
        private set

    fun startRide(): RideSessionState {
        require(state == RideSessionState.IDLE) {
            "Ride can start only from IDLE. current=$state"
        }
        state = RideSessionState.RUNNING
        return state
    }

    fun finishRide(): RideSessionState {
        require(state == RideSessionState.RUNNING) {
            "Ride can finish only from RUNNING. current=$state"
        }
        state = RideSessionState.FINISHED
        return state
    }

    fun reset(): RideSessionState {
        state = RideSessionState.IDLE
        return state
    }
}
