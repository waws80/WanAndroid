package com.thanatos.baselibrary.image

import android.widget.TextView

/**
 *  功能描述: 标题加载
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/26
 *  @updateTime: 2018/6/26 14:27
 */
interface ITitleLoader {

    fun load(titleView: TextView, data: Any)
}