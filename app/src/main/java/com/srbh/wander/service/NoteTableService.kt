package com.srbh.wander.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.srbh.wander.R
import com.srbh.wander.view.SplashActivity

class NoteTableService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val intent = Intent(this, SplashActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE )
        val notification = Notification.Builder(this,"channelId1")
            .setContentTitle("SQLite DB accessed")
            .setContentText("The table note of SQLite database note_db is currently in use.")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent).build()
        startForeground(1,notification)
        return START_STICKY
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val notificationChannel = NotificationChannel(
                 "channelId1",
                 "Foreground Notification",
                 NotificationManager.IMPORTANCE_DEFAULT
             )
            getSystemService(NotificationManager::class.java).also {
                it.createNotificationChannel(notificationChannel)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        stopForeground(true)
        stopSelf()
        super.onDestroy()
    }
}