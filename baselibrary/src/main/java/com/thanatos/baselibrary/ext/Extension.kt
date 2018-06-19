package com.thanatos.baselibrary.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.CropCircleTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * 设置状态栏（仅适用于使用v7包下的ToolBar页面）
 * 根布局必须设置 android:fitsSystemWindows="true"
 * @param color 状态栏设置的颜色
 * @param lightMode 状态栏字体是否为黑色
 */
@SuppressLint("ObsoleteSdkInt")
fun  Activity.setStatusBar(color: Int = Color.argb(180,0,0,0), lightMode: Boolean = false){

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color

    }else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT){
        val mContentView: ViewGroup = window.findViewById(Window.ID_ANDROID_CONTENT)
        mContentView.fitsSystemWindows = true
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        //根布局添加占位状态栏
        val statusBarView = View(this)
        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        getStatusBarHeight())
        statusBarView.setBackgroundColor(color)
        (window.decorView as ViewGroup).addView(statusBarView, lp)
    }

    if (lightMode){ //状态栏设置为黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or 去掉了
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }
}

/**
 * 获取状态栏高度
 */
fun getStatusBarHeight(): Int{
    var result = 0
    val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = Resources.getSystem().getDimensionPixelSize(resourceId)
    }
    return result
}

/**
 * dp转px
 */
fun Context.dp2px(dp: Int): Int{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp.toFloat(),resources.displayMetrics).toInt()
}

/**
 * sp转px
 */
fun Context.sp2px(sp: Int): Int{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp.toFloat(),resources.displayMetrics).toInt()
}

/**
 * px转dp
 */
fun Context.px2dp(px: Int): Int{
    val scale = resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

/**
 * px转sp
 */
fun Context.px2sp(px: Int): Int{
    val scale = resources.displayMetrics.scaledDensity
    return (px / scale + 0.5f).toInt()
}

/**
 * 加载圆形图片
 * iv.circle("http://img.zcool.cn/community/01664d5867d034a801219c77c8d449.gif",dp2px(50))
 */
fun ImageView.circle(url: String){

    Glide.with(this).load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(this)

}

/**
 * 加载圆角图片
 * iv.roundRect("http://img.zcool.cn/community/01664d5867d034a801219c77c8d449.gif",dp2px(50))
 */
fun ImageView.roundRect(url: String,round: Int = 18){
    Glide.with(this).load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(round,0)))
            .into(this)
}