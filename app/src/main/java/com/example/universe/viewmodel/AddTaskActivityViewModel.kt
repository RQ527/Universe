package com.example.universe.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.universe.bean.Task
import com.example.universe.model.repository.Repository

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/1
 */
class AddTaskActivityViewModel(application: Application):AndroidViewModel(application) {
    private val repository = Repository(application)
    fun insert(task: Task) { repository.insert(task)}
}