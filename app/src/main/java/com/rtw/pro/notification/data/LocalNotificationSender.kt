package com.rtw.pro.notification.data

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

/**
 * Local-only notification fallback for runtime smoke test.
 *
 * This avoids any dependency on FCM server-side sending being configured.
 */
object LocalNotificationSender {
    private const val CHANNEL_ID = "rtw_runtime_smoke"
    private const val CHANNEL_NAME = "RTW Runtime Smoke"

    fun trySend(context: Context, title: String, body: String): Boolean {
        try {
            if (Build.VERSION.SDK_INT >= 33) {
                val granted = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
                if (!granted) return false
            }

            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            ensureChannel(nm)

            val notificationId = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
            val notification: Notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .build()

            nm.notify(notificationId, notification)
            return true
        } catch (_: Exception) {
            return false
        }
    }

    private fun ensureChannel(nm: NotificationManager) {
        if (Build.VERSION.SDK_INT < 26) return
        if (nm.getNotificationChannel(CHANNEL_ID) != null) return
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        nm.createNotificationChannel(channel)
    }
}

