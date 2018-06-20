package com.thanatos.baselibrary.base

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.transition.Slide
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.thanatos.baselibrary.R
import com.thanatos.baselibrary.widget.ProgressToolbar

/**
 *  功能描述: activity基类
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/13
 *  @updateTime: 2018/6/13 11:41
 */
abstract class BaseActivity : AppCompatActivity() {

    private var mParentView: ViewGroup? = null

    private val mContentParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(layoutResID: Int) {
        initParentView()
        mParentView?.addView(View.inflate(this,layoutResID,null),mContentParams)

    }

    override fun setContentView(view: View) {
        initParentView()
        mParentView?.addView(view,mContentParams)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        initParentView()
        mParentView?.addView(view,params)
    }

    private fun initParentView(){
        super.setContentView(com.thanatos.baselibrary.R.layout.base_layout)
        if (mParentView == null){
            mParentView = findViewById(R.id.base_content)
            initErrorView()
        }
    }

    /**
     * 初始化空view
     */
    private fun initErrorView(){
        findViewById<Toolbar>(R.id.toolbar_empty).setNavigationOnClickListener { onBackPressed() }
        findViewById<View>(R.id.tv_empty_content).setOnClickListener { onErrorTextViewClick() }
    }

    protected fun onErrorTextViewClick(){}

    @SuppressLint("WrongViewCast")
    protected fun getErrorTextView(): TextView{
        return findViewById<TextView>(R.id.tv_empty_content)
    }


    protected fun showErrorView(@DrawableRes resourceId: Int = R.drawable.ic_empty_view,
                                text: String = resources.getString(R.string.app_empty)){
        checkContentView()
        findViewById<View>(R.id.base_empty).visibility = View.VISIBLE
        findViewById<View>(R.id.base_content).visibility = View.GONE
        findViewById<View>(R.id.view_empty_bg).setBackgroundResource(resourceId)
        findViewById<TextView>(R.id.tv_empty_content).text = text
    }

    protected fun hideErrorView(){
        checkContentView()
        findViewById<View>(R.id.base_empty).visibility = View.GONE
        findViewById<View>(R.id.base_content).visibility = View.VISIBLE
    }



    /**
     * 显示进度条
     */
    protected fun showProgressDialog(text: String = resources.getString(R.string.app_loading)){
        checkContentView()
        findViewById<View>(R.id.base_progress).visibility = View.VISIBLE
        findViewById<TextView>(R.id.tv_progress).text = text
    }

    /**
     * 隐藏进度条
     */
    protected fun hideProgressDialog(){
        checkContentView()
        findViewById<View>(R.id.base_progress).visibility = View.GONE
    }

    /**
     * 设置底部导航栏颜色
     */
    protected fun setNavigationColor(color: Int = Color.argb(60,0,0,0)){
        window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.navigationBarColor = color
    }

    /**
     * 校验是否加载布局文件
     */
    private fun checkContentView(){
        if (mParentView == null){
            throw NullPointerException("请先调用 setContentView 方法")
        }
    }


    /**
     * 日志
     */
    protected open fun log(msg: String, tag: String = "WanAndroid") {
        Log.d(tag, msg)
    }

    protected fun toast(text: String, duration: Int = Toast.LENGTH_SHORT,
                        @Slide.GravityFlag gravity: Int, x: Int = 0, y: Int = 0){
        val toast = Toast.makeText(applicationContext,text,duration)
        toast.setGravity(gravity,x,y)
        toast.show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(findViewById<View>(R.id.base_progress).visibility == View.VISIBLE){
            findViewById<View>(R.id.base_progress).visibility = View.GONE
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}