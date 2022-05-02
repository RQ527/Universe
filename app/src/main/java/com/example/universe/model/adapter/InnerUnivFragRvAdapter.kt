package com.example.universe.model.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.universe.R
import com.example.universe.bean.Task
import com.example.universe.databinding.ItemRvInnerTfragBinding
import com.example.universe.databinding.ItemRvInnerUfragBinding
import com.example.universe.model.selectPictures
import com.example.universe.view.AddTaskActivity

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/2
 */
class InnerUnivFragRvAdapter(val context: Context, val data: MutableList<Task>) :
    RecyclerView.Adapter<InnerUnivFragRvAdapter.MyHolder>() {
    inner class MyHolder(_binding: ItemRvInnerUfragBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding

        init {
            binding.ivRvInnerUFrag.setOnClickListener {
                listener?.onClicked(data[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {

        val binding = DataBindingUtil.inflate<ItemRvInnerUfragBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_rv_inner_ufrag,
            parent,
            false
        )
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        if (data.size != 0) {
            //随机变换图片的大小和距离，达到随机摆放效果
            val scale = ((1..1000).random() / 1000.0).toFloat()
            holder.binding.ivRvInnerUFrag.setImageResource(selectPictures(data[position].picture))
            Log.d("RQ", "onBindViewHolder: ${holder.binding.ivRvInnerUFrag.measuredWidth}")
            holder.binding.ivRvInnerUFrag.post {
                val params = LinearLayout.LayoutParams(
                    holder.binding.ivRvInnerUFrag.measuredWidth,
                    holder.binding.ivRvInnerUFrag.measuredHeight
                )
                params.leftMargin = (500*scale).toInt()
                holder.binding.ivRvInnerUFrag.layoutParams = params
                holder.binding.ivRvInnerUFrag.apply {
                    scaleX = scale + 1
                    scaleY = scale + 1
                }
            }

        }
    }

    override fun getItemCount(): Int = data.size
    private var listener: OnItemClickedListener? = null

    /**
     * 设置item监听
     */
    fun setOnClickedListener(l: OnItemClickedListener) {
        listener = l
    }

    interface OnItemClickedListener {
        fun onClicked(task: Task)
    }


}