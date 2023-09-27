package com.example.alarmclock

import android.annotation.SuppressLint
import android.app.*
import android.content.*
import android.media.*
import android.os.*
import android.widget.*
import androidx.core.app.*

class AlarmReceiver : BroadcastReceiver() {
    companion object{
        var mediaPlayer: MediaPlayer? = null
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the alarm is triggered

        // Create a notification channel (for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "alarm_channel"
            val channelName = "Alarm Channel"
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(notificationChannel)
        }

        // Create a PendingIntent to handle the "Stop" button click
        val stopIntent = Intent(context, StopAlarmReceiver::class.java)
        val pendingStopIntent = PendingIntent.getBroadcast(context, 1, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        // Create a custom notification layout
        val customNotificationLayout = RemoteViews(context.packageName, R.layout.custom_notification)
        customNotificationLayout.setOnClickPendingIntent(R.id.stopButton, pendingStopIntent)

        val notification = NotificationCompat.Builder(context, "alarm_channel")
            .setSmallIcon(R.drawable.bell)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setCustomContentView(customNotificationLayout) // Set the custom layout
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000)) // Vibrate pattern
            .build()

        // Show the notification
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(1, notification)

        // Play the alarm sound
        playAlarmSound(context)
    }

    private fun playAlarmSound(context: Context) {
        val mediaPlayer = MediaPlayer()
        val alarmSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        try {

            mediaPlayer.setDataSource(context, alarmSoundUri)
            mediaPlayer.setVolume(1.0f, 1.0f)
            mediaPlayer.isLooping = true
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
