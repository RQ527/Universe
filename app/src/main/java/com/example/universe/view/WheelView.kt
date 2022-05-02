package com.example.universe.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import com.example.universe.R
import kotlin.math.abs

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/5/1
 */
class WheelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    FrameLayout(context, attrs, defStyle) {
    private var textView = TextView(context)
    private val textViews = mutableListOf<TextView>()
    private var params: FrameLayout.LayoutParams? = null
    private var mHeight = 0
    private var mWidth = 0
    private var perHeight = 0
    private var detector:GestureDetector?=null
    var min = 0
    var max = 50

    init {
        for (i in min..max) {
            val mTv = TextView(context)
            mTv.apply {
                setLineSpacing(1.2f, 1.2f)
                setTextColor(resources.getColor(R.color.black))
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
                text = i.toString()
            }
            addView(mTv)
        }
        detector = GestureDetector(this.context,object :GestureDetector.SimpleOnGestureListener(){
            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent?,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                if (abs(distanceY)>0&& abs(distanceY)> abs(distanceX)){
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                scrollBy(0,distanceY.toInt())
                if (scrollY<0){
                    scrollY=0
                }else if (scrollY>mHeight){
                    scrollY=mHeight
                }
                return true
            }
        })
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mHeight = getMySize(100, heightMeasureSpec)
        mWidth = getMySize(100, heightMeasureSpec)
        perHeight = mWidth / 3
        params = FrameLayout.LayoutParams(mWidth, perHeight)
        for (i in 0 until childCount) {
            val childAt = getChildAt(i) as TextView
            childAt.layoutParams = params
            childAt.measure(childAt.measuredWidth, childAt.measuredHeight)
        }
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        for (i in 0 until childCount) {
            val child = getChildAt(i) as TextView
            child.layout(0, i * perHeight, mWidth, perHeight * (i + 1))
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        detector?.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }

    /**
     * 获取xml布局里的width和mHeight
     *
     * @param defaultSize 默认大小
     * @param measureSpec 解析的测量码
     * @return
     */
    private fun getMySize(defaultSize: Int, measureSpec: Int): Int {
        var mySize = defaultSize
        val mode = View.MeasureSpec.getMode(measureSpec) //测量模式
        val size = MeasureSpec.getSize(measureSpec) //测量尺寸
        when (mode) {
            MeasureSpec.UNSPECIFIED -> mySize = defaultSize
            MeasureSpec.AT_MOST, MeasureSpec.EXACTLY -> mySize = size
        }
        return mySize
    }
}