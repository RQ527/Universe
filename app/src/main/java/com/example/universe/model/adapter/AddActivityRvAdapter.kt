package com.example.universe.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.universe.R
import com.example.universe.bean.Task
import com.example.universe.databinding.ItemRvActivityAddBinding
import com.example.universe.databinding.ItemRvPopupwindowBinding
import com.example.universe.model.selectPictures

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/1
 */
class AddActivityRvAdapter() : RecyclerView.Adapter<AddActivityRvAdapter.MyHolder>() {
    val data = mutableListOf<Int>()
    var lastPosition = -1
    var selectedPosition = -1

    init {
        data.add(R.drawable.planet0)
        data.add(R.drawable.planet1)
        data.add(R.drawable.planet2)
        data.add(R.drawable.planet3)
        data.add(R.drawable.planet4)
        data.add(R.drawable.planet5)
    }

    inner class MyHolder(_binding: ItemRvActivityAddBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        val binding = _binding

        init {
            binding.ivAddActivityItem.setOnClickListener {
                listener?.onClicked(adapterPosition, lastPosition)

                selectedPosition = adapterPosition
                lastPosition = adapterPosition

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = DataBindingUtil.inflate<ItemRvActivityAddBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_rv_activity_add,
            parent,
            false
        )
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.binding.ivAddActivityItem.setImageResource(data[position])
    }

    override fun getItemCount(): Int = data.size
    private var listener: OnItemClickedListener? = null
    fun setOnClickedListener(l: OnItemClickedListener) {
        listener = l
    }

    interface OnItemClickedListener {
        fun onClicked(position: Int, lastPosition: Int)
    }

}