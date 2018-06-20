package com.thanatos.baselibrary.thread

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

/**
 *  功能描述: 线程类
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/20
 *  @updateTime: 2018/6/20 16:06
 */
object ThreadManager {

    val handler = Handler(Looper.getMainLooper())

    val thread = Executors.newFixedThreadPool(6)
}