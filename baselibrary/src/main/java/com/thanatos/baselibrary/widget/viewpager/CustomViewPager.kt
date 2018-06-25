package com.thanatos.baselibrary.widget.viewpager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import com.thanatos.baseres.R

/**
 *  功能描述: 自定义view pager
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/25
 *  @updateTime: 2018/6/25 10:31
 */
class CustomViewPager : ViewPager{

    var canScroll: Boolean = true

    var canSmoothScroll: Boolean  = true

    constructor(context: Context): super(context, null)

    constructor(context: Context,attributeSet: AttributeSet?):super(context,attributeSet){
        val array = context.obtainStyledAttributes(attributeSet,R.styleable.CustomViewPager)
        if (array.hasValue(R.styleable.CustomViewPager_horizontalCanScroll)){
            canScroll = array.getBoolean(R.styleable.CustomViewPager_horizontalCanScroll, false)
        }
        if (array.hasValue(R.styleable.CustomViewPager_canSmoothScroll)){
            canSmoothScroll = array.getBoolean(R.styleable.CustomViewPager_canSmoothScroll,false)
        }
        array.recycle()
    }


    override fun setCurrentItem(item: Int) {
        this.setCurrentItem(item,canSmoothScroll)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev) && canScroll
    }

}