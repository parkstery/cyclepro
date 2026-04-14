package com.rtw.pro.notification.data

import com.rtw.pro.notification.domain.PushMessage
import com.rtw.pro.notification.domain.PushNotifier

interface FcmClient {
    fun sendToTopic(topic: String, title: String, body: String): Boolean
}

class FcmPushNotifier(
    private val fcmClient: FcmClient
) : PushNotifier {
    override fun send(message: PushMessage): Boolean {
        return fcmClient.sendToTopic(
            topic = message.topic,
            title = message.title,
            body = message.body
        )
    }
}
