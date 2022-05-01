package com.example.universe.view

import android.app.Service
import android.content.Context
import android.graphics.*
import android.os.Vibrator
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.MutableLiveData
import kotlin.math.*

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/4/30
 */
class RingView(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatImageView(context, attrs) {
    private var mHeight: Int = 0
    private var mWidth: Int = 0
    private var ringPaint: Paint = Paint()
    private var pointPaint = Paint()
    private var detector: GestureDetector? = null
    var isBegin = false
    private var pointX = 0f
    private var pointY = 0f

    init {
        ringPaint.isAntiAlias = true
        ringPaint.color = Color.parseColor("#FF03DAC5")
        ringPaint.strokeWidth = 15f
        ringPaint.style = Paint.Style.STROKE
        pointPaint.isAntiAlias = true
        pointPaint.color = Color.parseColor("#FF03DAC5")
        pointPaint.strokeWidth = 15f
        pointPaint.style = Paint.Style.FILL
        detector =
            GestureDetector(this.context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onLongPress(e: MotionEvent?) {
                    val vib =
                        this@RingView.context.applicationContext.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
                    vib.vibrate(100)
                    listener?.longPressed()
                    isBegin = true
                }

            })
    }

    private var measureIsFinished = false
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mHeight = getMySize(100, heightMeasureSpec)
        mWidth = getMySize(100, widthMeasureSpec)
        setMeasuredDimension(mWidth, mHeight)
        if (!measureIsFinished) {
            pointX = (mWidth / 2).toFloat()
            pointY = 50f
            measureIsFinished = true
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(
            (mWidth / 2).toFloat(),
            (mHeight / 2).toFloat(),
            mWidth / 2 - 50f,
            ringPaint
        )
        drawPoint(pointX, pointY, canvas)
    }

    private fun drawPoint(centerX: Float, centerY: Float, canvas: Canvas) {
        canvas.drawCircle(centerX, centerY, 30f, pointPaint)
    }

    private var point = Point()
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val mX: Float
        val mY: Float
        detector?.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                mX = event.x
                mY = event.y
                if (isBegin) {

                    if (!isInRing(mX, mY)) {
                        point = calculatePoint(mX, mY)
                        pointX = point.x.toFloat()
                        pointY = point.y.toFloat()
                        minutes.value = calculateMinutes()

                        invalidate()
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                listener?.up()
                isBegin = false
            }
        }
        return true
    }

    private fun calculateMinutes(): Int {
        val angle =
            Math.toDegrees(atan(((-mWidth / 2 + pointX) / (pointY - mHeight / 2)).toDouble()))
                .toInt()
        return (((if (pointX > mWidth / 2) {
            if (pointY < mHeight / 2) -angle
            else 180 - angle
        } else {
            if (pointY > mHeight / 2) {
                180 - angle
            } else {
                360 - angle
            }
        }) / 360.0) * 120).toInt()
    }

    private fun isInRing(x: Float, y: Float): Boolean {
        val r2 =
            ((x - mWidth / 2) * (x - mWidth / 2) + (y - mHeight / 2) * (y - mHeight / 2)).toDouble()
        val r = sqrt(r2)
        return r <= mWidth / 2 - 400
    }

    private fun calculatePoint(x: Float, y: Float): Point {
        val R = (mWidth / 2 - 50f)
        val r2 =
            ((x - mWidth / 2) * (x - mWidth / 2) + (y - mHeight / 2) * (y - mHeight / 2)).toDouble()
        val r = sqrt(r2)
        val x0 = R * x / r + (1 - R / r) * (mWidth / 2)
        val y0 = R * y / r + (1 - R / r) * (mHeight / 2)
        return Point(x0.toInt(), y0.toInt())
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

    var minutes: MutableLiveData<Int> = MutableLiveData()

    /**
     * 设置分钟
     */
    fun setMinutes(minutes: Int) {
        this.minutes.value = minutes
        val R = mWidth / 2 - 50f
        val angle = (minutes / 120.0) * 360
        var x1 = (R * sin(Math.toRadians(angle)))
        var y1 = (R * cos(Math.toRadians(angle)))
        x1 += mWidth / 2
        y1 = mHeight / 2 - y1

        pointX = x1.toFloat()
        pointY = y1.toFloat()
        invalidate()
    }

    private var listener: OnStateListener? = null

    interface OnStateListener {
        fun longPressed()
        fun up()
    }

    fun setStateListener(l: OnStateListener) {
        listener = l
    }
}