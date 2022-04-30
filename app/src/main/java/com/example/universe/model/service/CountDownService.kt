package com.example.universe.model.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class CountDownService:Service() {
    private val binder = MyBinder()
    override fun onBind(p0: Intent?): IBinder = binder
    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    inner class MyBinder:Binder(){
        val service:CountDownService
        get() = this@CountDownService
    }
}