package com.example.universe.model.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.universe.R
import com.example.universe.bean.Task
import com.example.universe.databinding.FragmentInnerTodoBinding
import com.example.universe.databinding.ItemRvPopupwindowBinding
import com.example.universe.model.selectPictures
import com.example.universe.view.AddTaskActivity

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/1
 */
class PopupRvAdapter(val context: Context, var data: MutableList<Task>) :
    RecyclerView.Adapter<PopupRvAdapter.MyHolder>() {
    var lastPosition = -1
    var selectedPosition = -1

    inner class MyHolder(_binding: ItemRvPopupwindowBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding

        init {
            binding.ivPop.setOnClickListener {
                //排除最后的添加按钮
                if (adapterPosition != data.size) {
                    listener?.onClicked(adapterPosition, lastPosition)
                    selectedPosition = adapterPosition
                    lastPosition = adapterPosition
                } else if (adapterPosition == data.size) {
                    context.startActivity(Intent(context, AddTaskActivity::class.java))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {

        val binding = DataBindingUtil.inflate<ItemRvPopupwindowBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_rv_popupwindow,
            parent,
            false
        )

        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        if (position==data.size){
            holder.binding.tvPop.text = "创建星球"
            holder.binding.ivPop.setImageResource(R.drawable.add)
        }else {
            //末尾给出创建按钮
            holder.binding.tvPop.text = data[position].name
            holder.binding.ivPop.setImageResource(selectPictures(data[position].picture))
        }
    }

    override fun getItemCount(): Int = data.size + 1
    private var listener: OnItemClickedListener? = null
    fun setOnClickedListener(l: OnItemClickedListener) {
        listener = l
    }

    interface OnItemClickedListener {
        fun onClicked(position: Int, lastPosition: Int)
    }
}