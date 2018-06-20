package com.thanatos.wanandroid

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import com.thanatos.baselibrary.base.BaseActivity
import com.thanatos.baselibrary.ext.setStatusBar
import com.thanatos.baselibrary.ext.withRes
import com.thanatos.baselibrary.sputil.SpUtil
import com.thanatos.baselibrary.timer.CountDownTimerUtils
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

        setStatusBar(Color.WHITE,true)
        findViewById<ImageView>(R.id.splishView)
                .withRes(com.thanatos.baseres.R.mipmap.splish_bg)

        val tv = findViewById<TextView>(R.id.tv_jump)

        tv.setOnClickListener {
            Router.getInstance().path("/app_main")
            finish()
        }

        mCountDownTimerUtils = CountDownTimerUtils.getInstance()
        mCountDownTimerUtils.setTotalTime(6)
        mCountDownTimerUtils.start({
            tv.text = Html.fromHtml("跳过(<font color ='#455A64'>"+it+"s</font>)")
        },{
            Router.getInstance().path("/app_main")
            finish()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mCountDownTimerUtils.destory()
    }
}
