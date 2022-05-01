package com.example.universe.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.universe.base.BaseMvvmFragment
import com.example.universe.R
import com.example.universe.bean.Task
import com.example.universe.databinding.FragmentTimeCountBinding
import com.example.universe.model.adapter.PopupRvAdapter
import com.example.universe.model.service.CountDownService
import com.example.universe.viewmodel.TimeCountFragmentViewModel
import java.util.logging.Handler

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class TimeCountFragment() :
    BaseMvvmFragment<TimeCountFragmentViewModel, FragmentTimeCountBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_time_count
    var isRunning = false

    @SuppressLint("SetTextI18n")
    override fun afterCreate() {
        openPopupWindow()
        mViewDataBind.time = "00:00"
        mViewDataBind.ringView.minutes.observe(this, {
            val hour = (it / 60)
            val minutes = (it % 60)
            var str = ""
            str = if (hour == 2) {
                "00:00"
            } else {
                if (minutes < 10)
                    "0$hour:0$minutes"
                else "0$hour:$minutes"
            }
            mViewDataBind.time = str
        })

        mViewDataBind.ringView.setStateListener(object : RingView.OnStateListener {
            override fun longPressed() {
                mHandler.removeCallbacksAndMessages(null)
                isRunning = false
            }

            override fun up() {
                if (mViewDataBind.ringView.minutes.value != 0 && mViewDataBind.ringView.minutes.value != 120 && mViewDataBind.ringView.minutes.value != null) {
                    if (!isRunning) {
                        mHandler.postDelayed(
                            mRunnable,
                            0
                        )
                        isRunning = true
                    }
                } else {
                    showPopupWindow()
                }
            }
        })
    }

    fun decrease() {
        var minutes = mViewDataBind.ringView.minutes.value
        if (minutes == null) {
            minutes = 1
        }
        mViewDataBind.ringView.setMinutes(minutes - 1)
    }

    private val mRunnable = MyRunnable()
    private val mHandler = android.os.Handler()

    inner class MyRunnable : Runnable {
        override fun run() {
            if (mViewDataBind.ringView.minutes.value == 0) {
                isRunning = false
                mHandler.removeCallbacksAndMessages(null)
                this@TimeCountFragment.activity?.sendBroadcast(
                    Intent(
                        this@TimeCountFragment.activity,
                        CountDownService::class.java
                    )
                )
            } else {
                decrease()
                mHandler.postDelayed(this, 1000)
            }
        }
    }
    private lateinit var contentView:View
    private var popupWindow:PopupWindow? = null
    private fun openPopupWindow() {
        contentView =
            LayoutInflater.from(this.context).inflate(R.layout.popuwindow, null)
        val rv = contentView.findViewById<RecyclerView>(R.id.rv_popupWindow)
        val data = mutableListOf<Task>()
        data.add(Task(0, "创建星球", "add", "2022.4.30", "2022.4.31", "200min"))
        val linearLayoutManager = LinearLayoutManager(this.context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv.layoutManager = linearLayoutManager
        rv.adapter = PopupRvAdapter(data)

        popupWindow = PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindow?.setBackgroundDrawable(BitmapDrawable())
        popupWindow?.isFocusable = true
        popupWindow?.animationStyle = R.style.mypopwindow_anim_style
        popupWindow?.isOutsideTouchable = true
        popupWindow?.isTouchable = true

    }
    private fun showPopupWindow(){
        if (popupWindow?.isShowing == true){
            popupWindow?.dismiss()
        }else
        popupWindow?.showAtLocation(contentView,Gravity.BOTTOM,0,0)
    }
    private fun dismissPopupWindow() {
        if (popupWindow?.isShowing == true) {
            popupWindow?.dismiss()
        }
    }
    
}