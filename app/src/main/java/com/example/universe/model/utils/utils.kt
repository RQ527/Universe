package com.example.universe.model

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.TextView
import android.widget.Toast
import com.example.universe.R
import java.util.*

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/1
 */
fun selectPictures(name:String):Int{
    return when(name){
        "planet0"-> R.drawable.planet0
        "planet1"-> R.drawable.planet1
        "planet2"-> R.drawable.planet2
        "planet3"-> R.drawable.planet3
        "planet4"-> R.drawable.planet4
        "planet5"-> R.drawable.planet5
        "add"->R.drawable.add
        else -> R.drawable.planet0
    }
}
fun toast(context: Context,what:String){
    Toast.makeText(context,what,Toast.LENGTH_SHORT).show()
}
@SuppressLint("SetTextI18n")
fun openCalender(context: Context,textView: TextView){
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        null,
        calendar[Calendar.YEAR],
        calendar[Calendar.MONTH],
        calendar[Calendar.DAY_OF_MONTH]
    )
    datePickerDialog.show()

    // 确认按钮
    datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
        // 确认年月日
        val year = datePickerDialog.datePicker.year;
        val monthOfYear = datePickerDialog.datePicker.month + 1;
        val dayOfMonth = datePickerDialog.datePicker.dayOfMonth;
        textView.text = "$year.$monthOfYear.$dayOfMonth"
        // 关闭dialog
        datePickerDialog.dismiss()
    }
}