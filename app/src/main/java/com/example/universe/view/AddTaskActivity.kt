package com.example.universe.view


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import com.example.universe.R
import com.example.universe.base.BaseMvvmActivity
import com.example.universe.databinding.ActivityAddTaskBinding
import com.example.universe.viewmodel.AddTaskActivityViewModel

import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.content.Intent
import android.text.format.DateUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.universe.bean.Task
import com.example.universe.model.adapter.AddActivityRvAdapter
import com.example.universe.model.adapter.PopupRvAdapter
import com.example.universe.model.openCalender
import com.example.universe.model.selectPictures
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.TestOnly
import java.util.*


/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/1
 */
class AddTaskActivity() : BaseMvvmActivity<AddTaskActivityViewModel, ActivityAddTaskBinding>() {
    companion object {
        var id = 0
    }

    override fun getLayoutId(): Int = R.layout.activity_add_task

    @SuppressLint("SetTextI18n")
    override fun afterCreate() {
        val rvAdapter = AddActivityRvAdapter()
        mViewDataBind.rvAddActivityPlanet.adapter = rvAdapter
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mViewDataBind.rvAddActivityPlanet.layoutManager = linearLayoutManager
        rvAdapter.setOnClickedListener(object : AddActivityRvAdapter.OnItemClickedListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onClicked(position: Int, lastPosition: Int) {
                val lastViewHolder =
                    mViewDataBind.rvAddActivityPlanet.findViewHolderForLayoutPosition(lastPosition)
                lastViewHolder?.itemView?.setBackgroundResource(R.color.white)
                val viewHolder =
                    mViewDataBind.rvAddActivityPlanet.findViewHolderForLayoutPosition(position)
                viewHolder?.itemView?.setBackgroundResource(R.drawable.shape_tv)

            }
        })
        mViewDataBind.rvAddActivityPlanet.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val viewHolder =
                    recyclerView.findViewHolderForLayoutPosition(rvAdapter.lastPosition)
                viewHolder?.itemView?.setBackgroundResource(R.color.white)
            }
        })

        mViewDataBind.tvAddTaskDate.setOnClickListener {
            openCalender(this,mViewDataBind.tvAddTaskDate)
        }
        mViewDataBind.btAddActivity.setOnClickListener {
            when {
                mViewDataBind.evAddActivityName.text.toString() == "" -> {
                    Toast.makeText(this, "名称不可为空哦~", Toast.LENGTH_SHORT).show()
                }
                mViewDataBind.tvAddTaskDate.text.toString() == "选择日期" -> {
                    Toast.makeText(this, "日期不可为空哦~", Toast.LENGTH_SHORT).show()
                }
                mViewDataBind.evAddActivityRemarks.text.toString() == "" -> {
                    Toast.makeText(this, "备注不可为空哦~", Toast.LENGTH_SHORT).show()
                }
                rvAdapter.selectedPosition == -1 -> {
                    Toast.makeText(this, "请选择一张图片哦~", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val task = Task(
                        id++,
                        mViewDataBind.evAddActivityName.text.toString(),
                        "planet${rvAdapter.selectedPosition}",
                        mViewDataBind.tvAddTaskDate.text.toString(),
                        "未点亮",
                        "0",
                        mViewDataBind.evAddActivityRemarks.text.toString()
                    )
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            mViewModel.insert(task)
                        }
                        withContext(Dispatchers.Main){
                            finish()
                        }
                    }

                }
            }
        }
    }


}