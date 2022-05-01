package com.example.universe.model.repository

import android.content.Context
import com.example.universe.bean.Task
import com.example.universe.model.room.MyDataBase

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/1
 */
class RoomWork(context: Context) {
    private val dao = MyDataBase.getInstance(context).getDao()
    fun insertTask(task: Task)=dao.insertTask(task)
    fun deleteTask(task: Task)=dao.deleteTask(task)
    fun updateTask(task: Task)=dao.update(task)
    fun getAllTasks()=dao.getAllTasks()
}