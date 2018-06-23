package com.thanatos.baselibrary.user

import com.thanatos.baselibrary.ext.isEmpty
import com.thanatos.baselibrary.gson.GsonUtil
import com.thanatos.baselibrary.net.UserBean
import com.thanatos.baselibrary.sputil.SpUtil

/**
 * 用户信息获取类
 */
object UserInfo{

    /**
     * 是否登陆过
     */
    fun isLogin() = !SpUtil.get("userId","").isEmpty()

    /**
     * 获取用户信息
     */
    fun getUserInfo() = GsonUtil.getGson().fromJson(SpUtil.get("userBean",""),
            UserBean::class.java)

    /**
     * 获取用户id
     */
    fun getUserId() = getUserInfo().id

}
