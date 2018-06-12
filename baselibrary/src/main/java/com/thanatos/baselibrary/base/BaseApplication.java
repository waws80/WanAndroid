package com.thanatos.baselibrary.base;

import android.app.Application;
import android.content.Context;

import com.thanatos.baselibrary.BuildConfig;

import pw.androidthanatos.router.Router;
import top.waws.premission.DefaultIntecepter;
import top.waws.premission.PermissionUtil;

/**
 *  功能描述:
 *  @className: BaseApplication
 *  @author: thanatos
 *  @createTime: 2018/6/12
 *  @updateTime: 2018/6/12 15:00
 */
public class BaseApplication extends Application{

    private static Context  mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        //注册路由
        Router.init(this);
        Router.debug(BuildConfig.APP_DEBUG);

        //注册权限申请
        PermissionUtil.getInstance(BuildConfig.APP_DEBUG);
        PermissionUtil.setIntecepter(new DefaultIntecepter());
    }

    public static Context getContext(){
        return mContext;
    }
}
