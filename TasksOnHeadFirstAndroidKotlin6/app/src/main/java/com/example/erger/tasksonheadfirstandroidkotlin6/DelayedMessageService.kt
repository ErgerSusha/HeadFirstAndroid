package com.example.erger.tasksonheadfirstandroidkotlin6

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Handler

class DelayedMessageService : IntentService("DelayedMessageService") {
    companion object {
        const val EXTRA_MESSAGE: String = "EXTRA_MESSAGE"
        const val NOTIFICATION_ID: Int = 1
    }

    private var handler: Handler? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        handler = Handler()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        synchronized(this) {
            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        showText(intent!!.getStringExtra(EXTRA_MESSAGE))
    }

    private fun showText(text: String) {
        val stackBuilder = TaskStackBuilder.create(this)
                .addParentStack(MainActivity::class.java)
                .addNextIntent(Intent(this, MainActivity::class.java))
        val pendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification: Notification = Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.app_name))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setContentText(text)
                    .build()

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .notify(NOTIFICATION_ID, notification)
    }
}
// TODO: fix >= Oreo notifications