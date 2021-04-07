package com.example.nextgitter2.receiver

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.nextgitter2.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NotifierReceiver : BroadcastReceiver() {

    companion object {
        private const val CHANNEL_ID = "channel_1"
        private const val CHANNEL_NAME = "NextGitter Notifier"
        private const val NOTIFICATION_ID = 1
        private const val TIME_FORMAT = "HH:mm"
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_TYPE = "extra_type"
        const val REPEAT_ID = 100
    }

    override fun onReceive(context: Context, intent: Intent) {
        sendNotification(context)
    }

    private fun sendNotification(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage("com.example.nextgitter2")
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("Notifikasi NextGitter")
                .setContentText("Click aku, mari menemukan hal baru bersamaku!")
                .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun setRepeatNotifier(context: Context, type: String, time: String, message: String) {
        if (isDateInvalid(time, TIME_FORMAT)) return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotifierReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        val timeArr = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.apply {
            this.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArr[0]))
            this.set(Calendar.MINUTE, Integer.parseInt(timeArr[1]))
            this.set(Calendar.SECOND, 0)
        }
        val pendingIntent = PendingIntent.getBroadcast(context, REPEAT_ID, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)

        Toast.makeText(context, "Notifier set up successfully", Toast.LENGTH_SHORT).show()
    }

    fun cancelNotifier(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotifierReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, REPEAT_ID, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Notifier cancelled", Toast.LENGTH_SHORT).show()
    }

    private fun isDateInvalid(time: String, timeFormat: String): Boolean {
        return try {
            val df = SimpleDateFormat(timeFormat, Locale.getDefault())
            df.isLenient = false
            df.parse(time)
            false
        } catch (e: ParseException) {
            true
        }
    }
}