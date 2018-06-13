package com.thanatos.baselibrary.widget

import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintHelper
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import com.thanatos.baselibrary.ext.dp2px
import com.thanatos.baselibrary.ext.setStatusBar
import com.thanatos.baseres.R

/**
 *  功能描述: 自定义标题栏
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/13
 *  @updateTime: 2018/6/13 16:17
 */
class ProgressToolbar : ConstraintLayout{

    private lateinit var mNavParams: ConstraintLayout.LayoutParams

    //view
    private lateinit var mNavImageButton: AppCompatImageButton

    //attr
    private var mHeight: Int = 0
    private var mColor: Int = Color.rgb(255,255,255)

    private var mNavDrawable: Drawable? = null


    constructor(context: Context): super(context, null)

    constructor(context: Context, attributeSet: AttributeSet)
            : this(context,attributeSet, -1)

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int)
            : super(context,attributeSet,defStyleAttr){
        val array = context.obtainStyledAttributes(attributeSet,R.styleable.ProgressToolbar)
        initValue(array)
        array.recycle()
        initUI()
    }

    private fun initValue(array: TypedArray){
        mHeight = array.getDimensionPixelOffset(R.styleable.ProgressToolbar_toolbarHeight,
                context.dp2px(56))
        mColor = array.getColor(R.styleable.ProgressToolbar_toolbarBgColor,
                resources.getColor(R.color.colorPrimary))

        for (index in 0 until  array.indexCount){
            val attr = array.getIndex(index)
            if (attr == R.styleable.ProgressToolbar_toolbarNav){
                mNavDrawable = array.getDrawable(attr)
            }
        }
        if (mNavDrawable == null){
            mNavDrawable = resources.getDrawable(R.drawable.ic_back)
        }

    }

    private fun initUI(){

        initParent()

        initNavImageButton()

    }


    /**
     * 设置状态栏颜色和模式
     */
    fun setStatusBar(color: Int = Color.argb(60,0,0,0), light: Boolean = false){
        if (context is Activity){
            (context as Activity).setStatusBar(color,light)
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        var size = MeasureSpec.getSize(heightMeasureSpec)
        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST){
            //设置标题栏高度
            size = mHeight
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),size)

    }

    /**
     * 初始化容器
     */
    private fun initParent(){
        elevation = context.dp2px(6).toFloat()
        setBackgroundColor(mColor)
    }

    private fun initLeft(){

    }

    /**
     * https://www.itstrike.cn/Question/f39da80a-1c1d-4546-b8b6-1716d72391d8.html
     */
    private fun initNavImageButton(){
        //设置导航view大小
        mNavParams = ConstraintLayout.LayoutParams(context.dp2px(25),context.dp2px(25))
        val set = ConstraintSet()
        set.clone(this)
        set.centerVertically(id,0)
        set.applyTo(this)
        mNavParams.marginStart = context.dp2px(16)

        mNavImageButton = AppCompatImageButton(context,null,R.attr.toolbarNavigationButtonStyle)
        mNavImageButton.layoutParams = mNavParams
        mNavImageButton.setBackgroundResource(R.drawable.ic_launcher_background)
        mNavImageButton.isClickable = true
        addView(mNavImageButton)

        mNavDrawable.run {
            this?.setBounds(0,0,this.intrinsicWidth,this.intrinsicHeight)
            mNavImageButton.setImageDrawable(mNavDrawable)
        }
    }

}
