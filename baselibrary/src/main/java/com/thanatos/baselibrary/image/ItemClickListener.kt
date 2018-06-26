package com.thanatos.baselibrary.image

/**
 *  功能描述: 轮播图点击事件
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/26
 *  @updateTime: 2018/6/26 14:32
 */
interface ItemClickListener {

    /**
     * 轮播图条目点击事件
     * @param[position] 下标
     * @param[data] 下标数据
     */
    fun click(position: Int, data: Any)
}