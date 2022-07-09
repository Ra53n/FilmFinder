package com.example.filmfinder.maps

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.filmfinder.R

public class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        pushCinemaNotification(context)
    }

    private fun pushCinemaNotification(context: Context?) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setContentTitle("Внимание!")
            setSmallIcon(R.drawable.ic_baseline_camera_roll_24)
            setContentText("Кинотеатр находится рядом с вами!")
            priority = NotificationCompat.PRIORITY_HIGH
        }
        val channelName = "Cinema notification channel"
        val channelDescription =
            "Этот канал предназначен для уведомлений о вхождении в геозону кинотеатра"
        val channelPriority = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, channelName, channelPriority).apply {
            description = channelDescription
        }
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {
        private const val NOTIFICATION_ID = 12345
        private const val CHANNEL_ID = "CHANNEL_ONE"
    }
}