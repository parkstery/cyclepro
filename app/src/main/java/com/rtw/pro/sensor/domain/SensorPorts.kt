package com.rtw.pro.sensor.domain

interface SensorGateway {
    fun scan(): List<SensorDevice>
    fun connect(deviceId: String): Boolean
    fun disconnect()
}
