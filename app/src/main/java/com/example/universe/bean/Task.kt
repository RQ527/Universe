package com.example.universe.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/1
 */
@Entity(tableName = "task")
data class Task(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "name")
    val name:String,
    @ColumnInfo(name = "picture")
    val picture:String,
    @ColumnInfo(name = "expectedDate")
    val expectedDate:String,
    @ColumnInfo(name = "actualDate")
    val actualDate:String,
    @ColumnInfo(name = "duration")
    val duration:String
) {}
