package com.thanatos.baselibrary.widget

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.constraint.solver.widgets.ConstraintWidget
import android.support.constraint.solver.widgets.ConstraintWidgetContainer
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.AppCompatTextView
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.thanatos.baselibrary.ext.dp2px
import com.thanatos.baselibrary.ext.setStatusBar
import com.thanatos.baseres.R
import java.time.format.TextStyle
import java.util.*

/**
 *  功能描述: 自定义标题栏
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/13
 *  @updateTime: 2018/6/13 16:17
 */
class ProgressToolbar : ConstraintLayout{

    private lateinit var mNavParams: LayoutParams
    private lateinit var mProgressParams: LayoutParams
    private lateinit var mTitleParams: LayoutParams
    private lateinit var mMenuParams: LayoutParams
    private lateinit var mMenuGrouptParams: LayoutParams

    //view
    private lateinit var mNavImageButton: AppCompatImageButton
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mTitle: AppCompatTextView
    private lateinit var mMenu: AppCompatImageButton
    private lateinit var mMenuGroup: LinearLayout

    //attr
    private var mHeight: Int = 0
    private var mColor: Int = Color.rgb(255,255,255)

    private var mNavDrawable: Drawable? = null
    private var mMenuDrawable: Drawable? = null


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
            if (attr == R.styleable.ProgressToolbar_toolbarMenu){
                mMenuDrawable = array.getDrawable(attr)
            }
        }
        if (mNavDrawable == null){
            mNavDrawable = resources.getDrawable(R.drawable.ic_back)
        }
        if (mMenuDrawable == null){
            mMenuDrawable = resources.getDrawable(R.drawable.ic_menu)
        }

    }

    private fun initUI(){

        initParent()
        initNavImageButton()
        initProgressBar()
        initTitle()
        initMenu()
        initMenuGroup()

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
        id = R.id.toolbar_id
        elevation = context.dp2px(6).toFloat()
        setBackgroundColor(mColor)
    }

    private fun initProgressBar(){
        //初始化
        mProgressParams = LayoutParams(context.dp2px(32),context.dp2px(32))
        mProgressBar = ProgressBar(context)
        mProgressBar.layoutParams = mProgressParams
        addView(mProgressBar)
        mProgressBar.id = R.id.toolbar_progress_id
        mProgressBar.indeterminateTintList = ColorStateList.valueOf(Color.WHITE)

        //设置导航view的约束
        val set = ConstraintSet()
        set.clone(this)
        set.connect(mProgressBar.id,ConstraintSet.LEFT,id,ConstraintSet.LEFT,context.dp2px(10) * 2 + mNavParams.width)
        set.setMargin(mProgressBar.id,ConstraintSet.TOP,0)
        set.setMargin(mProgressBar.id,ConstraintSet.BOTTOM,0)
        set.centerVertically(mProgressBar.id,id)
        set.applyTo(this)
    }

    /**
     * https://www.itstrike.cn/Question/f39da80a-1c1d-4546-b8b6-1716d72391d8.html
     */
    private fun initNavImageButton(){
        //初始化
        mNavParams = LayoutParams(context.dp2px(32),context.dp2px(32))
        mNavImageButton = AppCompatImageButton(context)
        mNavImageButton.layoutParams = mNavParams
        mNavImageButton.setBackgroundResource(R.drawable.ripple_circle_bg)
        mNavImageButton.isClickable = true
        addView(mNavImageButton)

        mNavDrawable.run {
            this?.setBounds(0,0,this.intrinsicWidth,this.intrinsicHeight)
            mNavImageButton.setImageDrawable(mNavDrawable)
        }
        mNavImageButton.id = R.id.toolbar_nav_id
        //设置导航view的约束
        val set = ConstraintSet()
        set.clone(this)
        set.connect(mNavImageButton.id,ConstraintSet.START, id,ConstraintSet.START,context.dp2px(10))
        set.setMargin(mNavImageButton.id,ConstraintSet.TOP,0)
        set.setMargin(mNavImageButton.id,ConstraintSet.BOTTOM,0)
        set.centerVertically(mNavImageButton.id,id)
        set.applyTo(this)
    }

    private fun initTitle(){
        //初始化
        mTitleParams = LayoutParams(context.dp2px(180),mHeight)
        mTitle = AppCompatTextView(context)
        mTitle.layoutParams = mTitleParams
        mTitle.setSingleLine()
        mTitle.ellipsize = TextUtils.TruncateAt.END
        mTitle.gravity = Gravity.CENTER
        mTitle.setTextColor(resources.getColor(R.color.textColorPrimary))
        mTitle.textSize = 18f
        mTitle.paint.isFakeBoldText = true
        addView(mTitle)

        mTitle.id = R.id.toolbar_title_id
        //设置导航view的约束
        val set = ConstraintSet()
        set.clone(this)
        set.setMargin(mTitle.id,ConstraintSet.START,0)
        set.setMargin(mTitle.id,ConstraintSet.TOP,0)
        set.setMargin(mTitle.id,ConstraintSet.END,0)
        set.setMargin(mTitle.id,ConstraintSet.BOTTOM,0)
        set.centerVertically(mTitle.id,id)
        set.centerHorizontally(mTitle.id,id)
        set.applyTo(this)

        mTitle.text = "哈哈"
    }

    private fun initMenu(){
        mMenuParams = LayoutParams(context.dp2px(32),context.dp2px(32))
        mMenu = AppCompatImageButton(context)
        mMenu.layoutParams = mMenuParams
        mMenu.setBackgroundResource(R.drawable.ripple_circle_bg)
        mMenu.isClickable = true
        addView(mMenu)

        mMenuDrawable.run {
            this?.setBounds(0,0,this.intrinsicWidth,this.intrinsicHeight)
            mMenu.setImageDrawable(mMenuDrawable)
        }
        mMenu.id = R.id.toolbar_menu_id
        //设置menu的约束
        val set = ConstraintSet()
        set.clone(this)
        set.connect(mMenu.id,ConstraintSet.END, id,ConstraintSet.END,context.dp2px(10))
        set.setMargin(mMenu.id,ConstraintSet.TOP,0)
        set.setMargin(mMenu.id,ConstraintSet.BOTTOM,0)
        set.centerVertically(mMenu.id,id)

        set.applyTo(this)

    }

    private fun initMenuGroup(){
        mMenuGrouptParams = LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)
        mMenuGroup = LinearLayout(context)
        mMenuGroup.layoutParams = mMenuGrouptParams
        mMenuGroup.id = R.id.toolbar_menu_group_id

        mMenuGroup.orientation = LinearLayout.HORIZONTAL
        mMenuGroup.gravity = Gravity.CENTER_VERTICAL

        mMenuGroup.addView(Button(context))

        //设置menu的约束
        val set = ConstraintSet()
        set.clone(this)
        set.connect(mMenuGroup.id,ConstraintSet.END, id,ConstraintSet.END,0)
        set.setMargin(mMenuGroup.id,ConstraintSet.TOP,0)
        set.setMargin(mMenuGroup.id,ConstraintSet.BOTTOM,0)
        set.centerVertically(mMenuGroup.id,id)

        set.applyTo(this)


    }

}
