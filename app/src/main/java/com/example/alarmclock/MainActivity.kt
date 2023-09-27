package com.example.alarmclock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var currentTimeTextView: TextView
    private lateinit var setAlarmButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentTimeTextView = findViewById(R.id.currentTimeTextView)
        setAlarmButton = findViewById(R.id.setAlarmButton)

        // Display the current time and date
        updateTimeAndDate()

        // Set an alarm when the button is clicked
        setAlarmButton.setOnClickListener {
            setAlarm()
        }
    }

    private fun updateTimeAndDate() {
        val dateFormat = SimpleDateFormat("HH:mm\nEEE, dd MMM yyyy", Locale.getDefault())
        val currentTime = dateFormat.format(Date())
        currentTimeTextView.text = "Current Time:  $currentTime"
    }

    private fun setAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        // Set the alarm to trigger after 5 seconds (for testing)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.SECOND, 5)

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}
