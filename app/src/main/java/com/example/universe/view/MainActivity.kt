package com.example.universe.view

import androidx.viewpager2.widget.ViewPager2
import com.example.universe.base.BaseMvvmActivity
import com.example.universe.model.adapter.FragmentPagerAdapter
import com.example.universe.R
import com.example.universe.databinding.ActivityMainBinding
import com.example.universe.viewmodel.ActivityMainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseMvvmActivity<ActivityMainViewModel, ActivityMainBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun afterCreate() {
        //bottomNavigation设置
        mViewDataBind.navView.apply {
            labelVisibilityMode = BottomNavigationView.LABEL_VISIBILITY_LABELED
            setOnItemSelectedListener {
                if (mViewDataBind.viewPager.scrollState != ViewPager2.SCROLL_STATE_IDLE) return@setOnItemSelectedListener false
                when (it.itemId) {
                    R.id.counttime -> mViewDataBind.viewPager.currentItem = 0
                    R.id.universe -> mViewDataBind.viewPager.currentItem = 1
                    R.id.mine -> mViewDataBind.viewPager.currentItem = 2
                }
                return@setOnItemSelectedListener true
            }
        }
        //vp设置
        mViewDataBind.viewPager.adapter = FragmentPagerAdapter(this)
        mViewDataBind.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mViewDataBind.navView.menu.getItem(position).isChecked = true
            }
        })
        //禁用滚动
        mViewDataBind.viewPager.isUserInputEnabled = false
    }
}