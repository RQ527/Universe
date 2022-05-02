package com.example.universe.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.universe.R

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/2
 */
class InputDialog(context: Context?) : AlertDialog(context)  {
    var mCallBack: ClickCallBack? = null
    var mTextViewTitle: TextView? = null
    var mEditTextView: EditText? = null

    constructor(
        context: Context?,
        title: String?,
        callBack: ClickCallBack
    ) : this(context) {
        mCallBack = callBack
        if (title != null) mTextViewTitle?.text = title
    }

    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_input, null)
        setView(inflate)
        window?.setBackgroundDrawableResource(R.drawable.shape_tv)
        //设置点击别的区域不关闭页面
        setCancelable(false)
        mTextViewTitle = inflate.findViewById(R.id.tv_inputDialog_title)
        mEditTextView = inflate.findViewById(R.id.et_dialog_input)
        inflate.findViewById<View>(R.id.bt_inputDialog_sure)
            .setOnClickListener { mCallBack?.onYesClick(this,mEditTextView?.text?.toString()) }
        inflate.findViewById<View>(R.id.bt_inputDialog_cancel).setOnClickListener { dismiss() }
    }

    interface ClickCallBack {
        fun onYesClick(dialog: InputDialog,text:String?)
    }
}