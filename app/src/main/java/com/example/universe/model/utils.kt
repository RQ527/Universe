package com.example.universe.model

import com.example.universe.R

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/1
 */
fun selectPictures(name:String):Int{
    return when(name){
        "planet0"-> R.drawable.planet0
        "planet1"-> R.drawable.planet1
        "planet2"-> R.drawable.planet2
        "planet3"-> R.drawable.planet3
        "planet4"-> R.drawable.planet4
        "planet5"-> R.drawable.planet5
        "add"->R.drawable.add
        else -> R.drawable.planet0
    }
}