package com.thanatos.baselibrary.widget

import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.ViewGroup
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
class ProgressToolbar : RelativeLayout{

    private lateinit var mParams: ViewGroup.LayoutParams

    private lateinit var mNavParams: RelativeLayout.LayoutParams

    //view
    private lateinit var mNavImageButton: AppCompatImageButton

    //attr
    private var mHeight: Int = 0


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
        mHeight = array.getDimensionPixelOffset(R.styleable.ProgressToolbar_toolbarHeight,context.dp2px(56))


    }

    private fun initUI(){
        //设置标题栏高度
         mParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                 ViewGroup.LayoutParams.WRAP_CONTENT)
        mParams.height = mHeight

        //设置导航view大小
        mNavParams = RelativeLayout.LayoutParams(context.dp2px(25),context.dp2px(25))
        mNavParams.addRule(RelativeLayout.CENTER_VERTICAL)
        mNavParams.leftMargin = context.dp2px(16)

        mNavImageButton = AppCompatImageButton(context,null,R.attr.toolbarNavigationButtonStyle)
        mNavImageButton.layoutParams = mNavParams
        addView(mNavImageButton)

    }


    /**
     * 设置状态栏颜色和模式
     */
    fun setStatusBar(color: Int = Color.argb(60,0,0,0), light: Boolean = false){
        if (context is Activity){
            (context as Activity).setStatusBar(color,light)
        }
    }

}
