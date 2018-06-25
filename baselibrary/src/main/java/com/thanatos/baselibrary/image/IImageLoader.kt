package com.thanatos.baselibrary.image

import android.widget.ImageView

/**
 * 加载图片接口
 */
interface IImageLoader{
    fun load(imageView: ImageView, data: Any)
}
