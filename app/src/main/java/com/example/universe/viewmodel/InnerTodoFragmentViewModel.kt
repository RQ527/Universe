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
 * @date 2022/4/30
 */
class InnerTodoFragmentViewModel(application: Application):AndroidViewModel(application) {
    private val repository = Repository(application)
    fun getAll()=repository.getAll()
    fun update(task: Task)=repository.update(task)
    fun delete(task: Task)=repository.delete(task)
}