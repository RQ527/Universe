package com.example.universe.model.room

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.universe.bean.Task

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/3/30
 */
@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class MyDataBase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME: String = "my_db.db"
        private var mInstance: MyDataBase?=null
        @Synchronized
        fun getInstance(context: Context): MyDataBase{
            if (mInstance===null) mInstance = Room.databaseBuilder(
                context.applicationContext,
                MyDataBase::class.java, DATABASE_NAME
            ).build()
            return mInstance as MyDataBase
        }

    }
    abstract fun getDao(): MyDao
}