package com.example.universe.base

import android.graphics.Color
import androidx.annotation.LayoutRes

import androidx.databinding.DataBindingUtil

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat

import androidx.databinding.ViewDataBinding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType


/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
abstract class BaseMvvmActivity<VM : ViewModel, VDB : ViewDataBinding> :
    AppCompatActivity() {
    protected lateinit var mViewModel: VM
    protected lateinit var mViewDataBind: VDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mViewDataBind = DataBindingUtil.setContentView(this, getLayoutId())
        mViewDataBind.lifecycleOwner = this
        //获得泛型参数的实际类型
        val vmClass =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel =  ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(this.application)
        ).get(vmClass)
        cancelStatusBar()
        afterCreate()
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int
    protected abstract fun afterCreate()
    //CV自郭神
    private fun cancelStatusBar() {
        val window = this.window
        val decorView = window.decorView

        // 这是 Android 做了兼容的 Compat 包
        // 下面这个设置后会沉浸式状态栏和导航栏
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val windowInsetsController = ViewCompat.getWindowInsetsController(decorView)
        // 设置状态栏字体颜色为黑色
        windowInsetsController?.isAppearanceLightStatusBars = true
        //把状态栏颜色设置成透明
        window.statusBarColor = Color.TRANSPARENT
    }
}