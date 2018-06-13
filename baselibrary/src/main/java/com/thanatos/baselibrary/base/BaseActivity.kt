package com.thanatos.baselibrary.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 *  功能描述: activity基类
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/13
 *  @updateTime: 2018/6/13 11:41
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    /**
     * 日志
     */
    protected fun log(msg: String, tag: String = "WanAndroid") {
        Log.d(tag, msg)
    }


}