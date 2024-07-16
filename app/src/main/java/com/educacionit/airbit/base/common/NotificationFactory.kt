package com.educacionit.airbit.base.common

import android.app.Notification
import android.app.NotificationChannel
import android.content.Context
import android.app.NotificationManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.educacionit.airbit.R

object NotificationFactory {
    private var notificationManager: NotificationManager? = null
    private const val NOTIFICATION_ID = 1
    private const val CHANNEL_ID = "SHOW_ROOMS_IN_PLACE"
    private const val CHANNEL_NAME = "Show rooms on new location"
    private const val CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH
    fun showNotification(
        context: Context?,
        title: String,
        contentMessage: String) {
        notificationManager = notificationManager
            ?: context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        context?.let { safeContext ->
            val notification =
                buildNotification(safeContext, title, contentMessage)
            notificationManager?.notify(NOTIFICATION_ID, notification)
        }
    }

    private fun buildNotification(
        context: Context,
        title: String,
        contentMessage: String): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(contentMessage)
            .setSmallIcon(R.drawable.bed_icon)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.location_icon
                )
            )
            .setAutoCancel(true)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            CHANNEL_IMPORTANCE,
        )
        notificationManager?.createNotificationChannel(channel)
    }

}