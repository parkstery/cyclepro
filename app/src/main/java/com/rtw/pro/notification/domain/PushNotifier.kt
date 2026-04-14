package com.rtw.pro.notification.domain

data class PushMessage(
    val title: String,
    val body: String,
    val topic: String
)

interface PushNotifier {
    fun send(message: PushMessage): Boolean
}
