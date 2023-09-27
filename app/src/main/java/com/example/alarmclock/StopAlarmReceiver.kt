package com.example.alarmclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class StopAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the "Stop" button is clicked

        // Stop the alarm sound by releasing the MediaPlayer
        stopAlarmSound()
    }

    private fun stopAlarmSound() {
        // Check if the MediaPlayer instance is not null

        if (AlarmReceiver.mediaPlayer != null) {
            // Stop and release the MediaPlayer
            AlarmReceiver.mediaPlayer?.stop()
            AlarmReceiver.mediaPlayer?.release()
            AlarmReceiver.mediaPlayer = null // Set the reference to null
        }
    }
}
