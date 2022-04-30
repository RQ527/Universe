package com.example.universe.base

import androidx.annotation.LayoutRes

import android.os.Bundle

import androidx.lifecycle.ViewModelProviders

import androidx.databinding.DataBindingUtil

import android.view.ViewGroup

import android.view.LayoutInflater
import android.view.View

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModel
import java.lang.reflect.ParameterizedType


/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
abstract class BaseMvvmFragment<VM : ViewModel, VDB : ViewDataBinding> :
    Fragment() {
    protected lateinit var mViewModel: VM
    protected lateinit var mViewDataBind: VDB
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        state: Bundle?
    ): View? {
        mViewDataBind = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        mViewDataBind.lifecycleOwner = this
        //获得泛型参数的实际类型
        val vmClass =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProviders.of(this).get(vmClass)
        return mViewDataBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterCreate()
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int
    protected abstract fun afterCreate()
}