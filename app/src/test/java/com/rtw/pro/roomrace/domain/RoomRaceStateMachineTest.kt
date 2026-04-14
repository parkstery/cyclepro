package com.rtw.pro.roomrace.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class RoomRaceStateMachineTest {
    @Test
    fun transition_waiting_to_countdown_to_running_to_ended() {
        val machine = RoomRaceStateMachine()
        assertEquals(RoomRaceState.COUNTDOWN, machine.onHostStart())
        assertEquals(RoomRaceState.RUNNING, machine.onServerStartAtReached())
        assertEquals(RoomRaceState.ENDED, machine.onRaceEnded())
    }
}
