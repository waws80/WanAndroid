package com.thanatos.mine.mine.login.mvp

import android.support.annotation.NonNull
import com.thanatos.baselibrary.mvp.BaseView

/**
 *  功能描述: 登录模块view
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/22
 *  @updateTime: 2018/6/22 15:43
 */
interface LoginView: BaseView{

    /**
     * 登录成功回调函数
     */
    fun finishLogin()

}

/**
 *  功能描述: 登录presenter
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/22
 *  @updateTime: 2018/6/22 15:46
 */
interface ILoginPresenter{

    fun login(account: String, password: String)


}