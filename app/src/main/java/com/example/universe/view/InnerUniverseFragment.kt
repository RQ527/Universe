package com.example.universe.view

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.universe.R
import com.example.universe.base.BaseMvvmFragment
import com.example.universe.bean.Task
import com.example.universe.databinding.FragmentInnerUniverseBinding
import com.example.universe.model.adapter.InnerUnivFragRvAdapter
import com.example.universe.model.selectPictures
import com.example.universe.viewmodel.InnerUniverseFragmentViewModel

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class InnerUniverseFragment() :BaseMvvmFragment<InnerUniverseFragmentViewModel,FragmentInnerUniverseBinding>(){
    override fun getLayoutId(): Int = R.layout.fragment_inner_universe

    @SuppressLint("NotifyDataSetChanged")
    override fun afterCreate() {
        //rv相关
        val data = mutableListOf<Task>()
        val rvAdapter = InnerUnivFragRvAdapter(this.requireContext(), data)
        mViewDataBind.rvInnerUFragment.adapter = rvAdapter
        mViewDataBind.rvInnerUFragment.layoutManager = LinearLayoutManager(this.requireContext())

        mViewModel.getAll().observe(this, {
            val list = mutableListOf<Task>()
            for (i in it!!) {
                if (i.actualDate != "未点亮") {
                    list.add(i)
                }
            }
            Log.d("RQ", "afterCreate: ${list.size}")
            data.clear()
            data.addAll(list)
            rvAdapter.notifyDataSetChanged()
        })
        //监听
        rvAdapter.setOnClickedListener(object :InnerUnivFragRvAdapter.OnItemClickedListener{
            override fun onClicked(task: Task) {
                openPopupWindow(task)
                showPopupWindow()
            }
        })
    }
    private var popupWindow:PopupWindow?=null
    private lateinit var contentView:View
    @SuppressLint("SetTextI18n")
    private fun openPopupWindow(task: Task){
        contentView =
            LayoutInflater.from(this.context).inflate(R.layout.popuwindow_innerufrag, null)
        val picture = contentView.findViewById<ImageView>(R.id.iv_inUFragPop)
        val nameTv = contentView.findViewById<TextView>(R.id.tv_inUFragPop_name)
        val durationTv = contentView.findViewById<TextView>(R.id.tv_inUFragPop_duration)
        val dateTv = contentView.findViewById<TextView>(R.id.tv_inUFragPop_date)
        val remarksTv = contentView.findViewById<TextView>(R.id.tv_inUFragPop_remarks)
        picture.setImageResource(selectPictures(task.picture))
        nameTv.text = task.name
        durationTv.text = "${task.duration.toInt()/60}min${task.duration.toInt()%60}s"
        dateTv.text = task.actualDate
        remarksTv.text = task.remarks
        popupWindow = PopupWindow(
            contentView,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        popupWindow?.setBackgroundDrawable(BitmapDrawable())
        popupWindow?.isFocusable = true
        popupWindow?.animationStyle = R.style.mypopwindow_anim_style
        popupWindow?.isOutsideTouchable = true
        popupWindow?.isTouchable = true
    }
    private fun showPopupWindow() {
        if (popupWindow?.isShowing == true) {
            popupWindow?.dismiss()
        } else
            popupWindow?.showAtLocation(contentView, Gravity.BOTTOM, 0, 0)
    }

    private fun dismissPopupWindow() {
        if (popupWindow?.isShowing == true) {
            popupWindow?.dismiss()
        }
    }

    override fun onStop() {
        super.onStop()
        dismissPopupWindow()
    }
}