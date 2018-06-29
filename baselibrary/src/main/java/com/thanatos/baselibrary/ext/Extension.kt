package com.thanatos.baselibrary.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Handler
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.telecom.ConnectionService
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.thanatos.baselibrary.RegexUtil
import com.thanatos.baselibrary.base.BaseApplication
import com.thanatos.baselibrary.sputil.SpUtil
import com.thanatos.baselibrary.thread.ThreadManager
import com.thanatos.baseres.R
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.io.File
import java.time.Duration


val NO_NET = "当前网络不可用"



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

fun Activity.handler(): Handler{
    return ThreadManager.handler
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
 * 获取导航栏高度
 * @param context
 * @return
 */
fun Activity.getNavigationBarHeight(): Int {
    var resourceId = 0
    val rid = resources.getIdentifier("config_showNavigationBar", "bool", "android")
    return if (rid != 0 && hasNavigation()) {
        resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        resources.getDimensionPixelSize(resourceId)
    } else {
        0
    }
}


/**
 * 屏幕上是否有导航栏
 */
fun Activity.hasNavigation(): Boolean{
    val d = windowManager.defaultDisplay
    val realDisplayMetrics = DisplayMetrics()
    d.getRealMetrics(realDisplayMetrics)

    val realHeight = realDisplayMetrics.heightPixels
    val realWidth = realDisplayMetrics.widthPixels

    val displayMetrics = DisplayMetrics()
    d.getMetrics(displayMetrics)

    val displayHeight = displayMetrics.heightPixels
    val displayWidth = displayMetrics.widthPixels

    return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0
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
    Glide.with(this)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(this)

}

/**
 * 加载圆角图片
 * iv.roundRect("http://img.zcool.cn/community/01664d5867d034a801219c77c8d449.gif",dp2px(50))
 */
fun ImageView.roundRect(url: String,round: Int = 18){
    setImageDrawable(resources.getDrawable(R.drawable.ic_icon_picture_empty))
    Glide.with(this).load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(round,0)))
            .into(this)
}

/**
 * 加载资源文件
 */
fun ImageView.withRes(@DrawableRes id: Int, round: Int = 18){
    Glide.with(this).load(id)
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(round,0)))
            .into(this)
}

/**
 * 加载资源文件
 */
fun ImageView.withCircleRes(@DrawableRes id: Int){
    Glide.with(this).load(id)
            .apply(RequestOptions.circleCropTransform())
            .into(this)
}

/**
 * 下载图片
 */
fun downloadImage(url: String): File{
    val future = Glide.with(BaseApplication.context)
            .load(url)
            .downloadOnly(800,800)
    return  future.get()
}

/**
 * 将字符串保存到sp
 */
fun String.saveSp(@NonNull key: String){
    SpUtil.put(key,this)
}

fun isLogin(): Boolean{
  return  SpUtil.get("isLogin",false)
}

/**
 * 校验文本是否为空
 */
fun String?.isEmpty(): Boolean{
    if (this == null){
        return true
    }
    if (TextUtils.isEmpty(this.trim())){
        return true
    }
    if (this == "\"\""){
        return true
    }
    return false
}

/**
 * 打印日志
 */
fun printLog(msg: Any?){
    Log.d("WanAndroid", msg?.toString())
}

/**
 * 判断网络是否可用
 */
fun  hasNet(): Boolean{
    val ctxManager: ConnectivityManager? = BaseApplication.context.applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (null != ctxManager){
        val info: NetworkInfo? = ctxManager.activeNetworkInfo
        if (null != info && info.isConnected && info.state == NetworkInfo.State.CONNECTED){
            return true
        }
    }
    return false
}

/**
 * 是否是wifif
 */
fun isWifi(): Boolean{
    if (!hasNet()){
        return false
    }
    val ctxManager: ConnectivityManager? = BaseApplication.context.applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager ?: return false

    return ctxManager?.activeNetworkInfo?.type == ConnectivityManager.TYPE_WIFI
}

/**
 * 显示snackBar
 */
fun Activity.showSnackBarMsg(@NonNull msg: String, duration: Int = Snackbar.LENGTH_SHORT){
    val snackbar = Snackbar.make(window.decorView,msg,duration)
    snackbar.view.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
    snackbar.view.findViewById<TextView>(android.support.design.R.id.snackbar_text)
            .setTextColor(Color.WHITE)
    val navigatonBarHeight = getNavigationBarHeight()
    val params = snackbar.view.layoutParams as FrameLayout.LayoutParams
    params.bottomMargin = navigatonBarHeight
    snackbar.view.layoutParams = params
    snackbar.view.elevation = 0f
    snackbar.show()
}

/**
 * 显示[Toast]默认样式
 */
fun toast(@NonNull text: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(BaseApplication.context,text,duration).show()
}

/**
 * 显示[Toast]自定义样式
 */
fun toastView(@NonNull view: View, duration: Int = Toast.LENGTH_LONG, gravity: Int = Gravity.BOTTOM, x: Int, y: Int){
    val toast = Toast(BaseApplication.context)
    toast.view = view
    toast.duration = duration
    if (gravity != Gravity.BOTTOM){
        toast.setGravity(gravity,x,y)
    }
    toast.show()
}

