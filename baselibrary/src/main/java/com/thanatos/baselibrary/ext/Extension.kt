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
import android.view.WindowManager

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
        window.statusBarColor = color
    }else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT){
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //根布局添加占位状态栏
        val decorView = window.decorView as ViewGroup
        val statusBarView = View(this)
        val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        getStatusBarHeight())
        statusBarView.setBackgroundColor(color)
        decorView.addView(statusBarView, lp)
    }

    if (lightMode){ //状态栏设置为黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
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