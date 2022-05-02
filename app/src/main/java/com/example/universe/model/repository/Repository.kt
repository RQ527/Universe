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
class Repository(context: Context) {
    private val dao = MyDataBase.getInstance(context).getDao()
    fun insert(task: Task) {dao.insertTask(task)}
    fun delete(task: Task) { dao.deleteTask(task)}
    fun update(task: Task) { dao.updateTask(task)}
    fun getAll()=dao.getAllTasks()
}