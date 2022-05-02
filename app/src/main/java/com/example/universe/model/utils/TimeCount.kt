package com.example.universe.model.utils

import android.content.Intent
import com.example.universe.model.service.CountDownService
import com.example.universe.view.TimeCountFragment

/**
 * ... 倒计时(用handler实现)
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/2
 */
class TimeCount(val onRun:()->Unit) {
    fun cancel(){
        mHandler.removeCallbacksAndMessages(null)
    }
    fun start(delayMills:Long){
        mHandler.postDelayed(mRunnable,delayMills)
    }

    private val mRunnable = MyRunnable()
    private val mHandler = android.os.Handler()

    inner class MyRunnable : Runnable {
        override fun run() {
            onRun()
        }
    }
}