package com.example.universe.view

import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.example.universe.base.BaseMvvmFragment
import com.example.universe.R
import com.example.universe.databinding.FragmentUniverseBinding
import com.example.universe.model.adapter.InnerFragmentPagerAdapter
import com.example.universe.viewmodel.UniverseFragmentViewModel

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class UniverseFragment(): BaseMvvmFragment<UniverseFragmentViewModel, FragmentUniverseBinding>() {
    override fun getLayoutId(): Int  = R.layout.fragment_universe

    override fun afterCreate() {
        mViewDataBind.viewPager.adapter = InnerFragmentPagerAdapter(this.requireActivity())
        mViewDataBind.viewPager.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position==0){
                    mViewDataBind.tvInnerUniverse.isEnabled = false
                    mViewDataBind.tvInnerTodo.isEnabled = true
                }else if (position==1){
                    mViewDataBind.tvInnerTodo.isEnabled = false
                    mViewDataBind.tvInnerUniverse.isEnabled = true
                }
            }
        })
        mViewDataBind.tvInnerUniverse.setOnClickListener {
            mViewDataBind.viewPager.currentItem = 0
            mViewDataBind.tvInnerTodo.isEnabled = true
            it.isEnabled = false
        }
        mViewDataBind.tvInnerTodo.setOnClickListener {
            mViewDataBind.viewPager.currentItem = 1
            mViewDataBind.tvInnerUniverse.isEnabled = true
            it.isEnabled = false
        }
    }
}