package com.example.universe.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.universe.R
import com.example.universe.base.BaseMvvmActivity
import com.example.universe.databinding.ActivityAchieveMentBinding
import com.example.universe.viewmodel.AchievementActivityViewModel

class AchievementActivity : BaseMvvmActivity<AchievementActivityViewModel,ActivityAchieveMentBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_achieve_ment

    @SuppressLint("SetTextI18n")
    override fun afterCreate() {
        //计算成就
        mViewModel.getAll().observe(this,{
            var maxTime:Int=0
            var maxDay:Int=0
            for ( i in it!!){
                if (i.actualDate!="未点亮") {
                    val time = i.duration.toInt()
                    val day =
                        i.actualDate.split(".").last().toInt() - i.expectedDate.split(".").last()
                            .toInt()
                    if (time > maxTime) {
                        maxTime = time
                    }
                    if (day > maxDay) {
                        maxDay = day
                    }
                }
            }
            mViewDataBind.tvAchieveActivityTime.text = "${maxTime/60}min${maxTime%60}s"
            mViewDataBind.tvAchieveActivityAdvance.text = "你最早提前 $maxDay 天完成了任务"
        })
    }

}