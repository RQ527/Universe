package com.example.universe.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import java.util.ArrayList

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class MineFragmentViewModel(_application: Application):AndroidViewModel(_application) {
    private val mApplication = _application
    fun saveUserInfo(key:String,value:String){
        val sp = mApplication
            .getSharedPreferences("user", Context.MODE_PRIVATE)

        val edit = sp.edit()
        edit.putString(key, value)
        edit.apply()
    }
    fun getUserInfo():List<String> {
        val sp = mApplication
            .getSharedPreferences("user", Context.MODE_PRIVATE)
        val name = sp.getString("username", "网名")
        val saying = sp.getString("usersaying", "个性签名")
        val data = mutableListOf<String>()
        data.add(name ?: "")
        data.add(saying ?: "")
        return data
    }
}