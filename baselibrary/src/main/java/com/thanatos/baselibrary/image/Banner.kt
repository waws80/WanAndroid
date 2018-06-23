package com.thanatos.baselibrary.image

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.thanatos.baselibrary.ext.dp2px

/**
 * 轮播图
 */
class Banner(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1)
    : RelativeLayout(context, attrs, defStyleAttr) {

    //attr

    //轮播图片
    private val mRecyclerView: RecyclerView by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        RecyclerView(context)
    }

    //轮播图下标
    private val mLinearLayout: LinearLayout by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        LinearLayout(context)
    }

    //轮播图片params
    private val mContentParams: LayoutParams by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    //轮播图下标params
    private val mPointsParams: LayoutParams by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    private val mAdapter = BannerAdapter()

    init {
        //设置轮播图
        initUI()
    }

    //初始化布局
    private fun initUI(){
        //初始化图片
        mRecyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL,false)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.scrollToPosition(Int.MAX_VALUE/2)
        PagerSnapHelper().attachToRecyclerView(mRecyclerView)

        mContentParams.addRule(RelativeLayout.CENTER_IN_PARENT)

        mRecyclerView.layoutParams = mContentParams
        addView(mRecyclerView)

        //初始化图片下标
        mPointsParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        mPointsParams.height = context.dp2px(30)
        mLinearLayout.layoutParams = mPointsParams
        mLinearLayout.setBackgroundColor(Color.argb(60,0,0,0))
        mLinearLayout.gravity = Gravity.CENTER_VERTICAL
        addView(mLinearLayout)


    }
}