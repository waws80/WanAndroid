package com.thanatos.video.index

import com.thanatos.baselibrary.base.BaseFragment
import com.thanatos.baselibrary.ext.printLog
import com.thanatos.video.R

/**
 *  功能描述: 视频首页
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/25
 *  @updateTime: 2018/6/25 17:08
 */
class VideoIndexFragment : BaseFragment(){
    override fun onInit() {
        printLog("视频首页加载了")
    }

    override fun getContentView() = R.layout.video_fragment_index


}