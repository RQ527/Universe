package com.example.universe.view

import android.annotation.SuppressLint
import android.graphics.drawable.BitmapDrawable
import android.view.*
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.universe.R
import com.example.universe.base.BaseMvvmFragment
import com.example.universe.bean.Task
import com.example.universe.databinding.FragmentInnerTodoBinding
import com.example.universe.model.adapter.InnerTodoFragRvAdapter
import com.example.universe.model.openCalender
import com.example.universe.model.selectPictures
import com.example.universe.viewmodel.InnerTodoFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class InnerTodoFragment() :
    BaseMvvmFragment<InnerTodoFragmentViewModel, FragmentInnerTodoBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_inner_todo

    @SuppressLint("NotifyDataSetChanged")
    override fun afterCreate() {
        //rv相关操作
        val data = mutableListOf<Task>()
        val rvAdapter = InnerTodoFragRvAdapter(this.requireContext(), data)
        mViewDataBind.rvInnerTFragment.adapter = rvAdapter
        mViewDataBind.rvInnerTFragment.layoutManager = GridLayoutManager(this.requireContext(), 2)

        mViewModel.getAll().observe(this, {
            val list = mutableListOf<Task>()
            for (i in it!!) {
                if (i.actualDate == "未点亮") {
                    list.add(i)
                }
            }
            data.clear()
            data.addAll(list)
            rvAdapter.notifyDataSetChanged()
        })
        //item设置监听打开弹窗
        rvAdapter.setOnClickedListener(object : InnerTodoFragRvAdapter.OnItemClickedListener {
            override fun onClicked(task: Task) {
                openPopupWindow(task)
                showPopupWindow()
            }
        })
    }

    private var popupWindow: PopupWindow? = null
    private lateinit var contentView: View
    //加载弹窗
    @SuppressLint("SetTextI18n")
    private fun openPopupWindow(task: Task) {
        contentView =
            LayoutInflater.from(this.context).inflate(R.layout.popuwindow_innertfrag, null)
        val planetIv = contentView.findViewById<ImageView>(R.id.iv_inTFragPop)
        val nameTv = contentView.findViewById<TextView>(R.id.tv_inTFragPop_name)
        val durationLl = contentView.findViewById<LinearLayout>(R.id.ll_inTFragPop_duration)
        val dateLl = contentView.findViewById<LinearLayout>(R.id.ll_inTFragPop_date)
        val remarksLl = contentView.findViewById<LinearLayout>(R.id.ll_inTFragPop_remarks)
        val durationTv = contentView.findViewById<TextView>(R.id.tv_inTFragPop_duration)
        val dateTv = contentView.findViewById<TextView>(R.id.tv_inTFragPop_date)
        val remarksEt = contentView.findViewById<EditText>(R.id.et_inTFragPop_remarks)
        val lightBt = contentView.findViewById<Button>(R.id.bt_inTFragPop_light)
        val changBt = contentView.findViewById<Button>(R.id.bt_inTFragPop_change)
        val deleteBt = contentView.findViewById<Button>(R.id.bt_inTFragPop_delete)
        //初始化
        planetIv.setImageResource(selectPictures(task.picture))
        nameTv.text = task.name
        durationTv.text = "${task.duration.toInt()/60}min${task.duration.toInt()%60}s"
        dateTv.text = task.expectedDate
        remarksEt.setText(task.remarks)
        deleteBt.visibility = View.INVISIBLE
        dateTv.isClickable = false
        remarksEt.isFocusable = false
        remarksEt.isFocusableInTouchMode = false
        //事件逻辑处理
        dateTv.setOnClickListener {
            openCalender(this.requireContext(),dateTv)
        }
        changBt.setOnClickListener {
            lightBt.text = "修改"
            changBt.visibility = View.INVISIBLE
            deleteBt.visibility = View.VISIBLE
            durationLl.setBackgroundResource(R.drawable.shape_tv)
            dateLl.setBackgroundResource(R.drawable.shape_tv)
            remarksLl.setBackgroundResource(R.drawable.shape_tv)
            dateTv.isClickable = true
            remarksEt.isFocusable = true
            remarksEt.isFocusableInTouchMode = true
        }
        lightBt.setOnClickListener {
            if (lightBt.text == "修改") {
                task.expectedDate = dateTv.text.toString()
                task.remarks = remarksEt.text.toString()
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        mViewModel.update(task)
                    }
                }
                lightBt.text = "点亮"
                changBt.visibility = View.VISIBLE
                deleteBt.visibility = View.INVISIBLE
                durationLl.setBackgroundResource(R.color.none)
                dateLl.setBackgroundResource(R.color.none)
                remarksLl.setBackgroundResource(R.color.none)
                dateTv.isClickable = false
                remarksEt.isFocusable = false
                remarksEt.isFocusableInTouchMode = false
            } else if (lightBt.text == "点亮") {
                QueryDialog(this.requireContext(), "该星球即将被点亮，你确定吗？", object : QueryDialog.ClickCallBack {
                    override fun onYesClick(dialog: QueryDialog) {
                        val c = Calendar.getInstance()
                        val day = c.get(Calendar.DAY_OF_MONTH)
                        val month = c.get(Calendar.MONTH)
                        val year = c.get(Calendar.YEAR)
                        task.actualDate = "$year.$month.$day"
                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                mViewModel.update(task)
                            }
                        }
                        dialog.dismiss()
                        dismissPopupWindow()
                    }
                }).show()

            }
        }
        remarksLl.setOnClickListener {
            remarksEt.isFocusable = true
            remarksEt.isFocusableInTouchMode = true
            remarksEt.requestFocus()
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        }
        deleteBt.setOnClickListener {
            //弹出询问
            QueryDialog(this.requireContext(), "该星球即将被删除，你确定吗？", object : QueryDialog.ClickCallBack {
                override fun onYesClick(dialog: QueryDialog) {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            mViewModel.delete(task)
                        }
                    }
                    dialog.dismiss()
                    dismissPopupWindow()
                }
            }).show()
        }

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

    /**
     * 弹出窗口
     */
    private fun showPopupWindow() {
        if (popupWindow?.isShowing == true) {
            popupWindow?.dismiss()
        } else
            popupWindow?.showAtLocation(contentView, Gravity.BOTTOM, 0, 0)
    }

    /**
     * 关闭窗口
     */
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