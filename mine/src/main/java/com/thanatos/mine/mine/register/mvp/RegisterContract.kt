package com.thanatos.mine.mine.register.mvp

import com.thanatos.baselibrary.mvp.BasePresenter
import com.thanatos.baselibrary.mvp.BaseView

/**
 *  功能描述: 注册模块view
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/22
 *  @updateTime: 2018/6/22 15:43
 */
interface RegisterView: BaseView{

    /**
     * 完成注册
     */
    fun finishRegister()

}

/**
 *  功能描述: 注册模块presenter
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/22
 *  @updateTime: 2018/6/22 15:43
 */
interface IRegisterPresenter{

    /**
     * 去注册
     */
    fun register(account: String, password: String, repassword: String)
}