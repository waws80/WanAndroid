package com.thanatos.baselibrary.intecepter

import top.waws.premission.IPermissionIntecepter
import top.waws.premission.PermissionRequest

/**
 *  功能描述: 权限申请拦截器
 *  @className: WanAndroidPremissionIntecepter
 *  @author: thanatos
 *  @createTime: 2018/6/20
 *  @updateTime: 2018/6/20 17:14
 */
class WanAndroidPremissionIntecepter : IPermissionIntecepter {
    override fun start(permissionRequest: PermissionRequest?): Boolean {
        return true
    }

    override fun request(permissionRequest: PermissionRequest?) {
    }

    override fun hasRequested(permissionRequest: PermissionRequest?) {
    }

    override fun noAskComplete(permissionRequest: PermissionRequest?, dangers: MutableList<String>?) {
    }

    override fun complete(code: Int, dangers: MutableList<String>?) {
    }
}
