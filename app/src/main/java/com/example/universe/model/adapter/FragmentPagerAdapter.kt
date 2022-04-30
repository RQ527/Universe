package com.example.universe.model.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.universe.view.MineFragment
import com.example.universe.view.TimeCountFragment
import com.example.universe.view.UniverseFragment

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class FragmentPagerAdapter(_fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(_fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment =
        when(position){
            0-> TimeCountFragment()
            1->UniverseFragment()
            2->MineFragment()
            else -> error("Fragment Error")
        }


}