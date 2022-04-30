package com.example.universe.view

import com.example.universe.R
import com.example.universe.base.BaseMvvmFragment
import com.example.universe.databinding.FragmentInnerTodoBinding
import com.example.universe.viewmodel.InnerTodoFragmentViewModel

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class InnerTodoFragment():BaseMvvmFragment<InnerTodoFragmentViewModel,FragmentInnerTodoBinding>() {
    override fun getLayoutId(): Int  = R.layout.fragment_inner_todo

    override fun afterCreate() {
    }
}