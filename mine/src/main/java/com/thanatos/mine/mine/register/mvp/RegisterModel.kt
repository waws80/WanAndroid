package com.thanatos.mine.mine.register.mvp

import com.thanatos.baselibrary.mvp.BaseModel


class RegisterModel : BaseModel(){


    fun register(account: String, password: String, next:(Boolean, String) -> Unit){
        mRemoteData.articleData.register(account, password, next)
    }
}