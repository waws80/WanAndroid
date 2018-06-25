package com.thanatos.mine.index

import com.thanatos.baselibrary.base.BaseFragment
import com.thanatos.baselibrary.ext.printLog
import com.thanatos.mine.R

/**
 *  功能描述:MainIndexFragment
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/25
 *  @updateTime: 2018/6/25 16:26
 */
class MainIndexFragment : BaseFragment(){
    override fun onInit() {
        printLog("我的加载了")
    }

    override fun getContentView() = R.layout.mine_fragment_index


}
