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
    var id: Int,
    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "picture")
    var picture:String,
    @ColumnInfo(name = "expectedDate")
    var expectedDate:String,
    @ColumnInfo(name = "actualDate")
    var actualDate:String,
    @ColumnInfo(name = "duration")
    var duration:String,
    @ColumnInfo(name="remarks")
    var remarks:String
) {}
