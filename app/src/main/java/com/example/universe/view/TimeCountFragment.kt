package com.example.universe.view

import android.annotation.SuppressLint
import com.example.universe.base.BaseMvvmFragment
import com.example.universe.R
import com.example.universe.databinding.FragmentTimeCountBinding
import com.example.universe.viewmodel.TimeCountFragmentViewModel

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class TimeCountFragment(): BaseMvvmFragment<TimeCountFragmentViewModel, FragmentTimeCountBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_time_count

    @SuppressLint("SetTextI18n")
    override fun afterCreate() {
        mViewDataBind.ringView.minutes.observe(this,{
            val hour = (it/60)
            val minutes = (it%60)
            var str = ""
            str = if (hour==2){
                "00:00"
            }else{
                if (minutes<10)
                    "0$hour:0$minutes"
                else "0$hour:$minutes"
            }
            mViewDataBind.tvTime.text = str
        })

    }
}