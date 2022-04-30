package com.example.universe.view

import com.example.universe.base.BaseMvvmFragment
import com.example.universe.R
import com.example.universe.databinding.FragmentMineBinding
import com.example.universe.viewmodel.MineFragmentViewModel

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class MineFragment(): BaseMvvmFragment<MineFragmentViewModel, FragmentMineBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun afterCreate() {
    }
}