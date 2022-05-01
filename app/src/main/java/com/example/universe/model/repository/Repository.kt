package com.example.universe.model.repository

import android.content.Context
import com.example.universe.bean.Task

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/1
 */
class Repository(context: Context) {
    private val roomWork = RoomWork(context)
    fun insert(task: Task) = roomWork.insertTask(task)
    fun delete(task: Task) = roomWork.deleteTask(task)
    fun update(task: Task) = roomWork.updateTask(task)
    fun getAll()=roomWork.getAllTasks()
}