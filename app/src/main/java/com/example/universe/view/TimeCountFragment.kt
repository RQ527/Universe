package com.example.universe.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.universe.base.BaseMvvmFragment
import com.example.universe.R
import com.example.universe.bean.Task
import com.example.universe.databinding.FragmentTimeCountBinding
import com.example.universe.model.adapter.PopupRvAdapter
import com.example.universe.model.selectPictures
import com.example.universe.model.service.CountDownService
import com.example.universe.model.toast
import com.example.universe.viewmodel.TimeCountFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class TimeCountFragment() :
    BaseMvvmFragment<TimeCountFragmentViewModel, FragmentTimeCountBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_time_count
    var isRunning = false

    @SuppressLint("SetTextI18n")
    override fun afterCreate() {
        openPopupWindow()
        mViewModel.prepareTask {
            Log.d("RQ", "afterCreate: ${mViewDataBind.ringView.seconds.value}")
            if (mViewDataBind.ringView.seconds.value == 0) {

                isRunning = false
                mViewModel.timeCountStop()
                this@TimeCountFragment.activity?.sendBroadcast(
                    Intent(
                        this@TimeCountFragment.activity,
                        CountDownService::class.java
                    )
                )
            } else {
                decrease()
                mViewModel.timeCountStart(1000)
            }
        }
        mViewDataBind.time = "00:00:00"
        mViewDataBind.ringView.seconds.observe(this, {
            val hour = (it / 60 / 60)
            val minutes = (it / 60 % 60)
            val seconds = (it % 60)
            var str = ""
            str = if (hour == 2) {
                "00:00:00"
            } else {
                if (minutes < 10)
                    if (seconds < 10) "0$hour:0$minutes:0$seconds"
                    else "0$hour:0$minutes:$seconds"
                else
                    if (seconds < 10) "0$hour:$minutes:0$seconds"
                    else "0$hour:$minutes:$seconds"
            }
            mViewDataBind.time = str
        })

        mViewDataBind.ringView.setStateListener(object : RingView.OnStateListener {
            override fun longPressed() {
                mViewModel.timeCountStop()

                isRunning = false
            }

            override fun up() {
                if (mViewDataBind.ringView.seconds.value != 0 && mViewDataBind.ringView.seconds.value != (120 * 60) && mViewDataBind.ringView.seconds.value != null) {
                    if (mViewDataBind.ringView.tag == null) {
                        toast(this@TimeCountFragment.requireContext(), "请选择一个未点亮的星球哦~")
                        mViewDataBind.ringView.setMinutes(0)
                        return
                    }
                    if (!isRunning) {
                        mViewModel.timeCountStart(0)
                        isRunning = true
                    }
                } else {
                    //startActivity(Intent(this@TimeCountFragment.activity,AddTaskActivity::class.java))
                    if (!mViewDataBind.ringView.isBegin) showPopupWindow()
                }
            }
        })
    }

    fun decrease() {
        var seconds = mViewDataBind.ringView.seconds.value
        if (seconds == null) {
            seconds = 1
        }
        mViewDataBind.ringView.setMinutes(seconds - 1)
        if (mViewDataBind.ringView.tag != null) {
            val pos = mViewDataBind.ringView.tag as Int
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val task = data[pos]
                    task.duration =
                        "${task.duration.toInt() + 1}"
                    mViewModel.update(task)
                }
            }
        }
    }

    private lateinit var contentView: View
    private var popupWindow: PopupWindow? = null
    var popupRvAdapter: PopupRvAdapter? = null
    val data = mutableListOf<Task>()
    var popCardView: CardView? = null

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    private fun openPopupWindow() {
        contentView =
            LayoutInflater.from(this.context).inflate(R.layout.popuwindow_timecountfrag, null)
        val nameTv = contentView.findViewById<TextView>(R.id.tv_pop_name)
        val dateTv = contentView.findViewById<TextView>(R.id.tv_pop_date)
        val durationTv = contentView.findViewById<TextView>(R.id.tv_pop_duration)
        val button = contentView.findViewById<TextView>(R.id.bt_pop_change)
        val rv = contentView.findViewById<RecyclerView>(R.id.rv_popupWindow)
        popCardView = contentView.findViewById(R.id.cd_pop)
        popCardView?.visibility = View.INVISIBLE

        val linearLayoutManager = LinearLayoutManager(this.context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv.layoutManager = linearLayoutManager
        popupRvAdapter = PopupRvAdapter(this.requireActivity(), data)
        mViewModel.getAll().observe(this, {
            val list = mutableListOf<Task>()
            for (i in it!!) {
                list.add(i)
            }
            data.clear()
            data.addAll(list)
            popupRvAdapter?.notifyDataSetChanged()

        })

        popupRvAdapter?.setOnClickedListener(object : PopupRvAdapter.OnItemClickedListener {
            @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
            override fun onClicked(position: Int, lastPosition: Int) {
                val lastViewHolder = rv.findViewHolderForLayoutPosition(lastPosition)
                lastViewHolder?.itemView?.setBackgroundResource(R.color.white)
                val viewHolder = rv.findViewHolderForLayoutPosition(position)
                viewHolder?.itemView?.setBackgroundResource(R.drawable.shape_tv)
                popCardView?.visibility = View.VISIBLE
                nameTv.text = data[position].name
                dateTv.text = data[position].expectedDate
                durationTv.text = "${data[position].duration.toInt()/60}min${data[position].duration.toInt()%60}s"
            }
        })
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                popCardView?.visibility = View.INVISIBLE
                val viewHolder = rv.findViewHolderForLayoutPosition(popupRvAdapter!!.lastPosition)
                viewHolder?.itemView?.setBackgroundResource(R.color.white)
                popupRvAdapter?.selectedPosition = -1
            }
        })
        button.setOnClickListener {
            if (popupRvAdapter?.selectedPosition != -1) {
                mViewDataBind.ringView.setImageDrawable(resources.getDrawable(selectPictures(data[popupRvAdapter!!.selectedPosition].picture)))
                mViewDataBind.tvUnivFragName.text = data[popupRvAdapter!!.selectedPosition].name
                mViewDataBind.ringView.tag = popupRvAdapter?.selectedPosition
                popCardView?.visibility = View.INVISIBLE
                val viewHolder = rv.findViewHolderForLayoutPosition(popupRvAdapter!!.lastPosition)
                viewHolder?.itemView?.setBackgroundResource(R.color.white)
                popupRvAdapter?.selectedPosition = -1
                dismissPopupWindow()
            } else {
                toast(this.requireActivity(), "未选择~")
            }
        }
        rv.adapter = popupRvAdapter!!

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