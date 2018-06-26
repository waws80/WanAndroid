package com.thanatos.baselibrary.image

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.thanatos.baselibrary.R
import com.thanatos.baselibrary.ext.dp2px
import com.thanatos.baselibrary.thread.ThreadManager
import com.thanatos.baselibrary.timer.CountDownTimerUtils
import com.thanatos.baselibrary.widget.viewpager.CustomViewPager

/**
 * 轮播图
 */
class Banner  : RelativeLayout {

    private val DEFAULT_INDICATOR_BG = Color.argb(30,0,0,0)
    private val DEFAULT_INTERVAL = 5000
    private val DEFAULT_INDICATOR_HEIGHT: Int by lazy { context.dp2px(30) }

    //attr
    private var mIndicatorNormal: Drawable? = null
    private var mIndicatorSelector: Drawable? = null
    private var mIndicatorGravity: Int = Gravity.CENTER
    private var mIndicatorBgColor: Int = DEFAULT_INDICATOR_BG
    private var mIndicatorHeight: Int = 0
    private var mInterval: Int = DEFAULT_INTERVAL
    private var mAutoStart: Boolean = true
    private var mScaleType: ImageView.ScaleType = ImageView.ScaleType.FIT_XY
    private var mScroll: Boolean = false

    private var mCustomCanScroll: Boolean = false

    private var mCustomViewPager: CustomViewPager? = null

    //轮播图片
    private val mRecyclerView: CustomRecyclerView by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        CustomRecyclerView(context)
    }

    //轮播图下标
    private val mLinearLayout: LinearLayout by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        LinearLayout(context)
    }

    //标题
    private val mContentTitle: TextView by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        TextView(context)
    }

    //轮播图片params
    private val mContentParams: LayoutParams by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    //轮播图下标params
    private val mPointsParams: LayoutParams by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    //适配器
    private val mAdapter: BannerAdapter by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        BannerAdapter()
    }

    //轮播图轮询器
    private var timerUtils: CountDownTimerUtils? = CountDownTimerUtils.getInstance()

    var imageLoader: IImageLoader? = null

    var itemClickListener: ItemClickListener? = null

    var titleLoader: ITitleLoader? =null

    constructor(context: Context): this(context, null)

    constructor(context: Context,attrs: AttributeSet?): this(context, attrs, -1)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr){
        //解析attr
        initAttr(attrs)
        //设置轮播图
        initUI()

        //初始化customViewPager 事件拦截
        initPagerIntecepter()
    }


    //初始化属性
    private fun initAttr(attrs: AttributeSet?){
        val attrArr = context.obtainStyledAttributes(attrs, R.styleable.Banner)
        for (index in 0 until attrArr.indexCount){
            val attr = attrArr.getIndex(index)
            parasAttr(attrArr, attr)
        }
        attrArr.recycle()
    }

    /**
     * 解析属性
     */
    private fun parasAttr(attrArr: TypedArray, attr: Int) = when(attr){
        com.thanatos.baseres.R.styleable.Banner_indicator_normal ->
                mIndicatorNormal = attrArr.getDrawable(attr)
        com.thanatos.baseres.R.styleable.Banner_indicator_selector ->
                mIndicatorSelector = attrArr.getDrawable(attr)
        com.thanatos.baseres.R.styleable.Banner_indicator_gravity ->{
            val gravity = attrArr.getInt(attr,0)
            when(gravity){
                0 -> mIndicatorGravity = Gravity.LEFT
                1 -> mIndicatorGravity = Gravity.CENTER
                2 -> mIndicatorGravity = Gravity.RIGHT
                else ->{}
            }
        }
        com.thanatos.baseres.R.styleable.Banner_indicator_background ->
                mIndicatorBgColor = attrArr.getColor(attr,DEFAULT_INDICATOR_BG)
        com.thanatos.baseres.R.styleable.Banner_indicator_height ->
                mIndicatorHeight = attrArr.getDimension(attr,DEFAULT_INDICATOR_HEIGHT.toFloat()).toInt()
        com.thanatos.baseres.R.styleable.Banner_interval ->
                mInterval = attrArr.getInt(attr,DEFAULT_INTERVAL)
        com.thanatos.baseres.R.styleable.Banner_autoStart ->
                mAutoStart = attrArr.getBoolean(attr,false)
        com.thanatos.baseres.R.styleable.Banner_scaleType ->{
            val type = attrArr.getInt(attr,0)
            when(type){
                0 -> mScaleType = ImageView.ScaleType.FIT_XY
                1 -> mScaleType = ImageView.ScaleType.FIT_CENTER
                2 -> mScaleType = ImageView.ScaleType.CENTER
                3 -> mScaleType = ImageView.ScaleType.CENTER_CROP
                else ->{}
            }
        }
        else ->{ }
    }

    //初始化布局
    private fun initUI(){
        //初始化图片
        mContentParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        mRecyclerView.layoutParams = mContentParams
        addView(mRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL,false)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.scrollToPosition(Int.MAX_VALUE/2)
        PagerSnapHelper().attachToRecyclerView(mRecyclerView)
        initRecyclerViewThing()

        //初始化图片下标
        mPointsParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        mPointsParams.height = mIndicatorHeight
        mLinearLayout.layoutParams = mPointsParams
        mLinearLayout.orientation = LinearLayout.HORIZONTAL
        mLinearLayout.setBackgroundColor(mIndicatorBgColor)
        mLinearLayout.gravity = Gravity.CENTER_VERTICAL or mIndicatorGravity
        mLinearLayout.setPadding(context.dp2px(16),0,context.dp2px(16),0)
        addView(mLinearLayout)

        //初始化标题
        mContentTitle.layoutParams = mPointsParams
        mContentTitle.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
        mContentTitle.textSize = 14f
        mContentTitle.setTextColor(Color.WHITE)
        mContentTitle.setPadding(context.dp2px(16),0,context.dp2px(16),0)
        addView(mContentTitle)

    }

    /**
     * 获取自定义pager 并初始化滑动标记(解决首页轮播图和pager滑动冲突)
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

    /**
     * 设置rv的滚动事件和轮播图的轮询
     */
    private fun initRecyclerViewThing(){
        mRecyclerView.setOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(mAdapter.data.isEmpty()) return
                val position = (mRecyclerView.layoutManager as LinearLayoutManager)
                        .findFirstVisibleItemPosition()
                val p = position - Int.MAX_VALUE.shr(1)
                var realPosition = p % mAdapter.data.size
                if (realPosition < 0){
                    realPosition += mAdapter.data.size
                }
                if (mIndicatorNormal != null && mIndicatorHeight > 0){
                    for (index in 0 until mLinearLayout.childCount){
                        (mLinearLayout.getChildAt(index) as ImageView)
                                .setImageDrawable(mIndicatorNormal)
                    }
                    if (mIndicatorSelector != null){
                        (mLinearLayout.getChildAt(realPosition) as ImageView)
                                .setImageDrawable(mIndicatorSelector)
                    }
                }

                if (titleLoader != null){
                    titleLoader?.load(mContentTitle,mAdapter.data[realPosition])
                }
            }
        })

        //开始轮询
        if (timerUtils == null){
            timerUtils = CountDownTimerUtils.getInstance()
        }
        timerUtils?.setTotalTime(Int.MAX_VALUE)
        timerUtils?.setInterval(mInterval/1000)
        ThreadManager.handler.postDelayed({
            timerUtils?.start({
                if (mScroll && mAdapter.data.isNotEmpty()){
                    mRecyclerView.smoothScrollToPosition((mRecyclerView.layoutManager as LinearLayoutManager)
                            .findFirstVisibleItemPosition()+1)
                }

            },{})
        },mInterval.toLong())
    }

    /**
     * 添加数据
     */
    fun addList(@NonNull list: List<Any> = emptyList()){
        initPagerIntecepter()
        mAdapter.setList(list)
        mAdapter.scaleType
        if (list.isNotEmpty() && mIndicatorHeight > 0){
            mAdapter.imageLoader = imageLoader
            mAdapter.itemClickListener = itemClickListener
            mAdapter.mTitleView = mContentTitle
            //添加指示器
            if (mIndicatorNormal != null){
                mLinearLayout.removeAllViews()
                val indicatorParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                indicatorParams.rightMargin = context.dp2px(6)
                for (index in 0 until list.size){
                    val indicator = ImageView(context)
                    indicator.setImageDrawable(mIndicatorNormal)
                    indicator.scaleType = ImageView.ScaleType.FIT_XY
                    mLinearLayout.addView(indicator,indicatorParams)
                }
                (mLinearLayout.getChildAt(0) as ImageView)
                        .setImageDrawable(mIndicatorSelector)
            }
            //开始轮播
            if(mAutoStart){
                startScroll()
            }
        }
    }

    /**
     * 手指按下停止轮询 手指抬起重新开始轮询
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        when(ev.action){
            MotionEvent.ACTION_UP ->{
                mCustomViewPager?.canScroll = mCustomCanScroll
                startScroll()
            }
            MotionEvent.ACTION_DOWN ->{
                mCustomViewPager?.canScroll = true
                stopScroll()
            }
            else ->{
                mCustomViewPager?.canScroll = true
            }
        }
        return super.dispatchTouchEvent(ev)
    }


    /**
     * 开始轮询滚动
     */
    fun startScroll(){
        mScroll = true
    }

    /**
     * 停止轮询滚动
     */
    fun stopScroll(){
        mScroll = false
    }

    /**
     * 添加到活动窗口
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    /**
     * 从活动窗口移除的时候销毁定时器
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopScroll()
        timerUtils?.destory()
        timerUtils = null
    }


    inner class CustomRecyclerView(context: Context): RecyclerView(context){

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





}