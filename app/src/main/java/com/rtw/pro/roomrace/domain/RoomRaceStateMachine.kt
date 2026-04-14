package com.rtw.pro.roomrace.domain

class RoomRaceStateMachine(
    initialState: RoomRaceState = RoomRaceState.WAITING
) {
    var state: RoomRaceState = initialState
        private set

    fun onHostStart(): RoomRaceState {
        require(state == RoomRaceState.WAITING) { "Host start only allowed in WAITING. current=$state" }
        state = RoomRaceState.COUNTDOWN
        return state
    }

    fun onServerStartAtReached(): RoomRaceState {
        require(state == RoomRaceState.COUNTDOWN) { "StartAt transition only allowed in COUNTDOWN. current=$state" }
        state = RoomRaceState.RUNNING
        return state
    }

    fun onRaceEnded(): RoomRaceState {
        require(state == RoomRaceState.RUNNING || state == RoomRaceState.COUNTDOWN) {
            "Race end only allowed in COUNTDOWN/RUNNING. current=$state"
        }
        state = RoomRaceState.ENDED
        return state
    }
}
