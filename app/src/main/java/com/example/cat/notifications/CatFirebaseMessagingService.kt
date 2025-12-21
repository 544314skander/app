package com.example.cat.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.cat.MainActivity
import com.example.cat.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CatFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.i(TAG, "New FCM token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.notification?.title ?: message.data["title"] ?: "Cat Lounge"
        val body = message.notification?.body ?: message.data["body"] ?: "You have a new cat update."
        val catId = message.data["catId"]?.toIntOrNull()
        val destination = message.data["destination"]
        showNotification(title, body, catId, destination)
    }

    private fun showNotification(title: String, body: String, catId: Int?, destination: String?) {
        createChannelIfNeeded()

        val tapIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            if (catId != null) {
                putExtra("catId", catId.toString())
            }
            if (!destination.isNullOrBlank()) {
                putExtra("destination", destination)
            }
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            catId ?: 0,
            tapIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setColor(ContextCompat.getColor(this, R.color.notification_tint))
            .setContentIntent(pendingIntent)
            .build()

        with(NotificationManagerCompat.from(this)) {
            notify(catId ?: System.currentTimeMillis().toInt(), notification)
        }
    }

    private fun createChannelIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Cat updates",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Notifications about cat activity and tips."
            enableVibration(true)
        }
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    companion object {
        private const val TAG = "CatFCM"
        const val CHANNEL_ID = "cat_updates"
    }
}
