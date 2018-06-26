package com.thanatos.wanandroid

import android.app.ActivityOptions
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Html
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.TextView
import com.thanatos.baselibrary.base.BaseActivity
import com.thanatos.baselibrary.ext.setStatusBar
import com.thanatos.baselibrary.ext.withRes
import com.thanatos.baselibrary.sputil.SpUtil
import com.thanatos.baselibrary.timer.CountDownTimerUtils
import pw.androidthanatos.router.Request
import pw.androidthanatos.router.Router

/**
 *  功能描述: 闪屏页面
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/20
 *  @updateTime: 2018/6/20 15:28
 */
class SplishActivity : BaseActivity() {

    private lateinit var mCountDownTimerUtils: CountDownTimerUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splish)
        //设置状态栏
        setStatusBar(Color.WHITE,true)
        //设置导航栏
        setNavigationColor(Color.argb(30,0,0,0))
        findViewById<ImageView>(R.id.splishView)
                .withRes(com.thanatos.baseres.R.mipmap.splish_bg)

        val tv = findViewById<TextView>(R.id.tv_jump)

        tv.setOnClickListener {
            Router.getInstance().path("/app/main")
            finish()
            overridePendingTransition(android.R.anim.fade_in,0)
        }

        mCountDownTimerUtils = CountDownTimerUtils.getInstance()
        mCountDownTimerUtils.setTotalTime(6)
        mCountDownTimerUtils.start({
            tv.text = Html.fromHtml("跳过(<font color ='#455A64'>"+it+"s</font>)")
        },{
            Router.getInstance().path("/app/main")
            finish()
            overridePendingTransition(android.R.anim.fade_in,0)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mCountDownTimerUtils.destory()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
