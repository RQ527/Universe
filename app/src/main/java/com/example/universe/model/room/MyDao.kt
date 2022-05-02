package com.example.universe.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.universe.bean.Task

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/1
 */
@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTask(task: Task)

    @Query("SELECT * FROM task")
    fun getAllTasks():LiveData<List<Task>>
}