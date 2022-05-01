package com.example.universe.model.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.app.NotificationChannel

import android.content.Context.NOTIFICATION_SERVICE

import androidx.core.content.ContextCompat.getSystemService

import android.app.NotificationManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.app.NotificationManagerCompat

import androidx.core.app.NotificationCompat

import android.R
import android.annotation.SuppressLint

import android.app.PendingIntent
import com.example.universe.view.MainActivity


/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class CountDownService : BroadcastReceiver() {
    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(p0: Context?, p1: Intent?) {
        val intent = Intent(p0, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent: PendingIntent
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(p0, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            pendingIntent = PendingIntent.getActivity(p0, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        }
        val channelId = createNotificationChannel(
            "my_channel_ID",
            "my_channel_NAME",
            NotificationManager.IMPORTANCE_HIGH,
            p0
        )
        val notification: NotificationCompat.Builder = NotificationCompat.Builder(p0!!, channelId!!)
            .setContentTitle("通知")
            .setContentText("收到一条消息")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.sym_def_app_icon)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        val notificationManager = NotificationManagerCompat.from(p0)
        notificationManager.notify(100, notification.build())
    }

    private fun createNotificationChannel(
        channelID: String,
        channelNAME: String,
        level: Int,
        context: Context?
    ): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
            val channel = NotificationChannel(channelID, channelNAME, level)
            manager!!.createNotificationChannel(channel)
            channelID
        } else {
            null
        }
    }

}