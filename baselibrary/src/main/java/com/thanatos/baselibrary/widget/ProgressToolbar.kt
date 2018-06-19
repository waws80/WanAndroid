package com.thanatos.baselibrary.widget

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.support.v7.widget.AppCompatImageButton
import android.support.v7.widget.AppCompatTextView
import android.text.TextUtils
import android.util.AttributeSet
import android.view.*
import android.widget.*
import com.thanatos.baselibrary.ext.dp2px
import com.thanatos.baselibrary.ext.px2sp
import com.thanatos.baselibrary.ext.setStatusBar
import com.thanatos.baselibrary.ext.sp2px
import com.thanatos.baseres.R

/**
 *  功能描述: 自定义标题栏
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/13
 *  @updateTime: 2018/6/13 16:17
 */
open class ProgressToolbar : RelativeLayout{

    private lateinit var mNavParams: LayoutParams
    private lateinit var mProgressParams: LayoutParams
    private lateinit var mTitleParams: LayoutParams
    private lateinit var mMenuParams: LayoutParams
    private lateinit var mMenuGroupParams: LayoutParams

    //view
    private lateinit var mNavImageButton: AppCompatImageButton
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mTitle: AppCompatTextView
    private lateinit var mMenu: AppCompatImageButton
    private lateinit var mMenuGroup: LinearLayout

    //attr
    private var mHeight: Int = 0
    private var mColor: Int = Color.rgb(255,255,255)

    private var mShowNav: Boolean = false
    private var mShowMenu: Boolean = false
    private var mShowProgressBar: Boolean = false

    private var mTitleText: String = ""
    private var mTitleTextColor: Int = Color.WHITE
    private var mTItleBoldStyle: Boolean = false
    private var mTitleTextSize: Float = 18f
    private var mTitleGravity: Int = Gravity.CENTER

    private var mStatusBarColor: Int = Color.BLACK
    private var mStatusBarMode: Int = 1

    private var mNavDrawable: Drawable? = null
    private var mMenuDrawable: Drawable? = null


    constructor(context: Context): this(context,null)

    constructor(context: Context, attributeSet: AttributeSet?)
            : super(context,attributeSet){
        val array = context.obtainStyledAttributes(attributeSet,R.styleable.ProgressToolbar)
        initValue(array)
        array.recycle()
        initUI()
    }

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

        mShowNav = array.getBoolean(R.styleable.ProgressToolbar_toolbarShowNav,false)
        mShowMenu = array.getBoolean(R.styleable.ProgressToolbar_toolbarShowMenu,false)
        mShowProgressBar = array.getBoolean(R.styleable.ProgressToolbar_toolbarShowProgress,false)

        if(array.hasValue(R.styleable.ProgressToolbar_toolbarTitle)){
            mTitleText = array.getString(R.styleable.ProgressToolbar_toolbarTitle)
        }
        mTitleTextColor = array.getColor(R.styleable.ProgressToolbar_toolbarTitleColor,Color.WHITE)
        val size = array.getDimension(R.styleable.ProgressToolbar_toolbarTitleSize,context.sp2px(18).toFloat())
        mTitleTextSize = context.px2sp(size.toInt()).toFloat()
        mTItleBoldStyle = array.getBoolean(R.styleable.ProgressToolbar_toolbarTitleBold,false)
        mTitleGravity = array.getInt(R.styleable.ProgressToolbar_toolbarTitleGravity,Gravity.CENTER)

        mStatusBarColor = array.getColor(R.styleable.ProgressToolbar_statusBarColor,resources.getColor(R.color.colorPrimaryDark))
        if (array.hasValue(R.styleable.ProgressToolbar_statusBarMode)){
            mStatusBarMode = array.getInt(R.styleable.ProgressToolbar_statusBarMode,1)
        }

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

        setStatusBar(mStatusBarColor,mStatusBarMode == 0)

    }


    /**
     * 设置状态栏颜色和模式
     */
    public fun setStatusBar(color: Int = Color.argb(60,0,0,0),
                            light: Boolean = false): ProgressToolbar{
        if (context is Activity){
            (context as Activity).setStatusBar(color,light)
        }
        return this
    }

    public fun setNavIcon(@DrawableRes drawableId: Int): ProgressToolbar{
        setNavIcon(resources.getDrawable(drawableId))
        return this
    }

    public fun setNavIcon(@NonNull drawable: Drawable): ProgressToolbar{
        if (mNavImageButton.visibility == View.GONE){
            mNavImageButton.visibility = View.VISIBLE
            if (mProgressBar.visibility == View.VISIBLE){
                mProgressParams.leftMargin = context.dp2px(45)
                if (mTitleGravity == Gravity.LEFT){
                    mTitleParams.leftMargin = context.dp2px(80)
                }
            }else{
                if (mTitleGravity == Gravity.LEFT){
                    mTitleParams.leftMargin = context.dp2px(45)
                }
            }
        }

        mNavImageButton.setImageDrawable(drawable)

        mNavImageButton.layoutParams = mNavParams
        mProgressBar.layoutParams = mProgressParams
        mTitle.layoutParams = mTitleParams

        return this
    }

    public fun hideNav(): ProgressToolbar{
        if(mNavImageButton.visibility == View.VISIBLE){
            mNavImageButton.visibility = View.GONE
            if (mProgressBar.visibility == View.VISIBLE){
                mProgressParams.leftMargin = context.dp2px(10)
                mProgressBar.layoutParams = mProgressParams
                if (mTitleGravity == Gravity.LEFT){
                    mTitleParams.leftMargin = context.dp2px(45)
                    mTitle.layoutParams = mTitleParams
                }
            }else{
                if (mTitleGravity == Gravity.LEFT){
                    mTitleParams.leftMargin = context.dp2px(10)
                    mTitle.layoutParams = mTitleParams
                }
            }

        }
        return this
    }

    public fun showProgress(color: Int = Color.WHITE): ProgressToolbar{
        if (mProgressBar.visibility == View.GONE){
            mProgressBar.visibility = View.VISIBLE
            if (mNavImageButton.visibility == View.VISIBLE){
                mProgressParams.leftMargin = context.dp2px(45)
                if (mTitleGravity == Gravity.LEFT){
                    mTitleParams.leftMargin = context.dp2px(80)
                }
            }else{
                mProgressParams.leftMargin = context.dp2px(10)
                if (mTitleGravity == Gravity.LEFT){
                    mTitleParams.leftMargin = context.dp2px(45)
                }
            }
        }
        mNavImageButton.layoutParams = mNavParams
        mProgressBar.layoutParams = mProgressParams
        mTitle.layoutParams = mTitleParams

        mProgressBar.indeterminateTintList = ColorStateList.valueOf(color)

        return this
    }

    public fun hideProgress(): ProgressToolbar{
        if (mProgressBar.visibility == View.VISIBLE){
            mProgressBar.visibility = View.GONE
            if (mTitleGravity == Gravity.LEFT ){

                if (mNavImageButton.visibility == View.VISIBLE){
                    mTitleParams.leftMargin = context.dp2px(45)
                }else{
                    mTitleParams.leftMargin = context.dp2px(10)
                }
                mTitle.layoutParams = mTitleParams
            }
        }
        return this
    }

    public fun setMenuIcon(@DrawableRes drawableId: Int): ProgressToolbar{
        setMenuIcon(resources.getDrawable(drawableId))
        return this
    }

    public fun setMenuIcon(@NonNull drawable: Drawable): ProgressToolbar{
        if (mMenu.visibility == View.GONE){
            mMenu.visibility = View.VISIBLE
            mMenuGroupParams.rightMargin = context.dp2px(40)
            mMenuGroup.layoutParams = mMenuGroupParams
        }

        mMenu.setImageDrawable(drawable)

        return this
    }

    /**
     * 隐藏右边菜单按钮
     */
    public fun hideRightMenu(): ProgressToolbar{
        if (mMenu.visibility == View.VISIBLE){
            mMenu.visibility = View.GONE
            mMenuGroupParams.rightMargin = context.dp2px(10)
            mMenuGroup.layoutParams = mMenuGroupParams
        }
        return this
    }

    public fun showMenuGroup(): ProgressToolbar{
        mMenuGroup.visibility = View.VISIBLE
        return this
    }

    public fun showAllMenu(): ProgressToolbar{
        mMenu.visibility = View.VISIBLE
        mMenuGroup.visibility = View.VISIBLE
        return this
    }

    public fun hideMenuGroup(): ProgressToolbar{
        mMenuGroup.visibility = View.GONE
        return this
    }

    public fun hideAllMenu(): ProgressToolbar{
        mMenu.visibility = View.GONE
        mMenuGroup.visibility = View.GONE
        return this
    }

    public fun clearMenuGroup(): ProgressToolbar{
        val count = mMenuGroup.childCount
        if (count > 0){
            mMenuGroup.removeAllViews()
        }
        return this
    }

    public fun addMenu(text: String, textColor: Int = Color.WHITE, textSize: Float = 12f,
                       drawable: Drawable?, click:()-> Unit): ProgressToolbar{
        if (mMenuGroup.childCount >= 2)return this
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        params.height = context.dp2px(25)
        params.leftMargin = context.dp2px(10)
        val menu: TextView = AppCompatTextView(context)
        menu.text = text
        menu.setTextColor(ColorStateList.valueOf(textColor))
        menu.textSize = textSize
        menu.background = drawable
        menu.gravity = Gravity.CENTER
        menu.layoutParams = params
        menu.isClickable = true
        mMenuGroup.addView(menu)
        menu.setOnClickListener {
            click.invoke()
        }

        return this
    }

    public fun addMenu(drawable: Drawable, click:()-> Unit): ProgressToolbar{
        if (mMenuGroup.childCount >= 2)return this
        val params = LinearLayout.LayoutParams(context.dp2px(25),
                context.dp2px(25))
        params.leftMargin = context.dp2px(10)
        val menu = AppCompatImageButton(context)
        menu.setImageDrawable(drawable)
        menu.setBackgroundResource(R.drawable.ripple_circle_bg)
        menu.layoutParams = params
        menu.isClickable = true
        mMenuGroup.addView(menu)
        menu.setOnClickListener{
            click.invoke()
        }
        return this
    }

    @Nullable
    public fun <V: View> getMenu(@NonNull gravity: Int): V?{
        if (mMenuGroup.childCount == 0){
            return null
        }
        if (mMenuGroup.childCount == 1){
            return mMenuGroup.getChildAt(0) as V
        }
        if (gravity == Gravity.LEFT || gravity == Gravity.START){
            return mMenuGroup.getChildAt(0) as V
        }
        if (gravity == Gravity.RIGHT || gravity == Gravity.END){
            return mMenuGroup.getChildAt(1) as V
        }
        return null
    }

    public fun setNavClickListener(click: () -> Unit){
        setNavIcon(mNavDrawable!!)
        mNavImageButton.setOnClickListener { click.invoke() }

    }

    public fun setMenuClickListener(click: () -> Unit){
        setMenuIcon(mMenuDrawable!!)
        mMenu.setOnClickListener { click.invoke() }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

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


    private fun initNavImageButton(){
        //初始化
        mNavParams = LayoutParams(context.dp2px(25),context.dp2px(25))
        mNavParams.addRule(RelativeLayout.CENTER_VERTICAL)
        mNavParams.leftMargin = context.dp2px(10)
        mNavImageButton = AppCompatImageButton(context)
        mNavImageButton.layoutParams = mNavParams
        mNavImageButton.setBackgroundResource(R.drawable.ripple_circle_bg)
        mNavImageButton.isClickable = true
        addView(mNavImageButton)
        mNavImageButton.visibility = if (mShowNav) View.VISIBLE else View.GONE

        mNavDrawable.run {
            this?.setBounds(0,0,this.intrinsicWidth,this.intrinsicHeight)
            mNavImageButton.setImageDrawable(mNavDrawable)
        }
        mNavImageButton.id = R.id.toolbar_nav_id
    }

    private fun initProgressBar(){
        //初始化
        mProgressParams = LayoutParams(context.dp2px(25),context.dp2px(25))
        mProgressParams.addRule(RelativeLayout.CENTER_VERTICAL)
        var leftMargin: Int = context.dp2px(10)
        if (mNavImageButton.visibility == View.VISIBLE){
            leftMargin = context.dp2px(45)
        }
        mProgressParams.leftMargin = leftMargin
        mProgressBar = ProgressBar(context)
        mProgressBar.layoutParams = mProgressParams
        addView(mProgressBar)
        mProgressBar.visibility = if (mShowProgressBar) View.VISIBLE else View.GONE
        mProgressBar.id = R.id.toolbar_progress_id
        mProgressBar.indeterminateTintList = ColorStateList.valueOf(Color.WHITE)

    }

    private fun initTitle(){
        //初始化
        mTitleParams = LayoutParams(LayoutParams.WRAP_CONTENT,mHeight)
        if (mTitleGravity == Gravity.CENTER){
            mTitleParams.addRule(RelativeLayout.CENTER_IN_PARENT)
        }else{
            mTitleParams.addRule(RelativeLayout.CENTER_VERTICAL)
            if (mNavImageButton.visibility == View.VISIBLE && mProgressBar.visibility == View.VISIBLE){
                mTitleParams.leftMargin = context.dp2px(80)
            }else if (mNavImageButton.visibility == View.VISIBLE || mProgressBar.visibility == View.VISIBLE){
                mTitleParams.leftMargin = context.dp2px(45)
            }else{
                mTitleParams.leftMargin = context.dp2px(10)
            }
        }

        mTitle = AppCompatTextView(context)
        mTitle.layoutParams = mTitleParams
        mTitle.maxWidth = context.dp2px(160)
        mTitle.setSingleLine()
        mTitle.ellipsize = TextUtils.TruncateAt.END
        mTitle.gravity = Gravity.CENTER
        mTitle.setTextColor(mTitleTextColor)
        mTitle.textSize = mTitleTextSize
        mTitle.paint.isFakeBoldText = mTItleBoldStyle
        mTitle.text = mTitleText
        addView(mTitle)

        mTitle.id = R.id.toolbar_title_id

    }

    private fun initMenu(){
        mMenuParams = LayoutParams(context.dp2px(25),context.dp2px(25))
        mMenuParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        mMenuParams.addRule(RelativeLayout.CENTER_VERTICAL)
        mMenuParams.rightMargin = context.dp2px(10)
        mMenu = AppCompatImageButton(context)
        mMenu.layoutParams = mMenuParams
        mMenu.setBackgroundResource(R.drawable.ripple_circle_bg)
        mMenu.isClickable = true
        addView(mMenu)
        mMenu.visibility = if(mShowMenu) View.VISIBLE else View.GONE
        mMenuDrawable.run {
            this?.setBounds(0,0,this.intrinsicWidth,this.intrinsicHeight)
            mMenu.setImageDrawable(mMenuDrawable)
        }
        mMenu.id = R.id.toolbar_menu_id

    }

    private fun initMenuGroup(){
        mMenuGroupParams = LayoutParams(LayoutParams.WRAP_CONTENT,context.dp2px(25))
        mMenuGroupParams.addRule(RelativeLayout.CENTER_VERTICAL)
        mMenuGroupParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        var rightMargin: Int = context.dp2px(10)
        if (mMenu.visibility == View.VISIBLE){
            rightMargin = context.dp2px(40)
        }
        mMenuGroupParams.rightMargin = rightMargin

        mMenuGroup = LinearLayout(context)
        mMenuGroup.layoutParams = mMenuGroupParams
        mMenuGroup.id = R.id.toolbar_menu_group_id

        mMenuGroup.orientation = LinearLayout.HORIZONTAL
        mMenuGroup.gravity = Gravity.CENTER

        addView(mMenuGroup)
    }


}
