package com.thanatos.baselibrary.image

import android.widget.ImageView
import android.widget.TextView

/**
 * 加载图片接口
 */
interface IImageLoader{
    /**
     * 加载图片
     * @param [imageView] //加载图片控件
     * @param [data] 集合下标数据
     */
    fun load(imageView: ImageView, data: Any)
}
