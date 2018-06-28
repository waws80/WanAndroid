package com.thanatos.baselibrary.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

import com.thanatos.baselibrary.BuildConfig
import com.thanatos.baselibrary.intecepter.WanAndroidPremissionIntecepter
import com.thanatos.baselibrary.intecepter.WanAndroidRouterIntecepter
import com.thanatos.baselibrary.mvp.PresenterProviders

import pw.androidthanatos.router.Router
import top.waws.premission.DefaultIntecepter
import top.waws.premission.PermissionUtil
import kotlin.properties.Delegates

/**
 * 功能描述:
 * @className: BaseApplication
 * @author: thanatos
 * @createTime: 2018/6/12
 * @updateTime: 2018/6/12 15:00
 */
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this

        //注册路由
        Router.init(this)
        Router.debug(BuildConfig.APP_DEBUG)
        //路由拦截器
        Router.addIntecepter(WanAndroidRouterIntecepter())

        //注册权限申请
        PermissionUtil.getInstance(BuildConfig.APP_DEBUG)
        //权限拦截器
        PermissionUtil.setIntecepter(WanAndroidPremissionIntecepter())

        //初始化程序生命周期
        PresenterProviders.init(this)
    }

    companion object {
        var context: Application by Delegates.notNull()
            private set
    }

}
