package com.thanatos.baselibrary.widget

import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent
import com.thanatos.baselibrary.widget.viewpager.CustomViewPager

/**
 *  功能描述: 自定义scrollview解决和CustomViewPager的滑动冲突
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/27
 *  @updateTime: 2018/6/27 16:55
 */
class CustomNestedScrollView : NestedScrollView{

    var mCustomCanScroll = false

    var mCustomViewPager: CustomViewPager? = null

    constructor(context: Context): this(context,null)

    constructor(context: Context, attributeSet: AttributeSet?): this(context,attributeSet,-1)

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int):
            super(context,attributeSet,defStyle){
    }

    /**
     * 获取自定义pager 并初始化滑动标记(解决和pager滑动冲突)
     */
    private fun initPagerIntecepter(){
        var p = parent
        while (p != null && p !is CustomViewPager){
            p = p.parent
        }
        if (p != null && p is CustomViewPager) {
            mCustomCanScroll = p.canScroll
            mCustomViewPager = p
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initPagerIntecepter()
    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        when(ev.action){
            MotionEvent.ACTION_UP ->{
                if (!mCustomCanScroll){
                    mCustomViewPager?.canScroll = mCustomCanScroll
                }
            }
            else ->{
                mCustomViewPager?.canScroll = true
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}