package com.thanatos.mine.mine.mvp

import android.support.annotation.NonNull
import com.thanatos.baselibrary.mvp.BaseModel

/**
 *  功能描述: 登录模块获取数据model
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/22
 *  @updateTime: 2018/6/22 16:00
 */
class LoginModel : BaseModel(){

    /**
     * 进行登录数据获取
     */
    fun login(@NonNull account: String, @NonNull password: String, next:(Boolean, String) -> Unit){
        mRemoteData.articleData.login(account,password,next)
    }
}