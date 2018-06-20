package com.thanatos.baselibrary.intecepter

import pw.androidthanatos.router.Request
import pw.androidthanatos.router.RouterIntecepter

/**
 *  功能描述:
 *  @className: WanAndroidRouterIntecepter
 *  @author: thanatos
 *  @createTime: 2018/6/20
 *  @updateTime: 2018/6/20 17:11
 */
class WanAndroidRouterIntecepter : RouterIntecepter {
    override fun chain(request: Request): Request {
        return  request
    }

    override fun onLost(msg: String) {

    }

    override fun onSuccess() {

    }
}
