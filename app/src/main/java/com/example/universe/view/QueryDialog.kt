package com.example.universe.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.universe.R

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/2
 */
@SuppressLint("InflateParams")
class QueryDialog(context: Context?) : AlertDialog(context) {
    var mCallBack: ClickCallBack? = null
    var mTextViewContent: TextView? = null

    constructor(
        context: Context?,
        content: String?,
        callBack: ClickCallBack
    ) : this(context) {
        mCallBack = callBack
        if (content != null) mTextViewContent?.text = content
    }

    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_query, null)
        setView(inflate)
        window?.setBackgroundDrawableResource(R.drawable.shape_tv)
        //设置点击别的区域不关闭页面
        setCancelable(false)
        mTextViewContent = inflate.findViewById(R.id.tv_dialog_content)
        inflate.findViewById<View>(R.id.bt_dialog_ensure)
            .setOnClickListener { mCallBack?.onYesClick(this) }
        inflate.findViewById<View>(R.id.bt_dialog_cancel).setOnClickListener { dismiss() }
    }

    interface ClickCallBack {
        fun onYesClick(dialog: QueryDialog)
    }

}