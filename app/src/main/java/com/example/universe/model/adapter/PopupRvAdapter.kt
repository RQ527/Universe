package com.example.universe.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.universe.R
import com.example.universe.bean.Task
import com.example.universe.databinding.FragmentInnerTodoBinding
import com.example.universe.databinding.ItemRvPopupwindowBinding
import com.example.universe.model.selectPictures

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/1
 */
class PopupRvAdapter(val data:List<Task>):RecyclerView.Adapter<PopupRvAdapter.MyHolder>() {
    inner class MyHolder(_binding: ItemRvPopupwindowBinding):RecyclerView.ViewHolder(_binding.root){
        val binding = _binding
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
        holder.binding.tvPop.text = data[position].name
        holder.binding.ivPop.setImageResource(selectPictures(data[position].picture))
    }

    override fun getItemCount(): Int  = data.size

}