package com.example.universe.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.universe.bean.Task
import com.example.universe.model.repository.Repository
import com.example.universe.model.utils.TimeCount

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class TimeCountFragmentViewModel(application: Application):AndroidViewModel(application) {
    private val repository = Repository(application)
    fun insert(task: Task) { repository.insert(task)}
    fun delete(task: Task){repository.delete(task)}
    fun update(task: Task){repository.update(task)}
    fun getAll()=repository.getAll()
    private var timeCount:TimeCount? = null
    fun prepareTask(onRun:()->Unit){
        timeCount = TimeCount(onRun)
    }
    fun timeCountStart(delayMills:Long){
        timeCount?.start(delayMills)
    }
    fun timeCountStop(){
        timeCount?.cancel()
    }
}