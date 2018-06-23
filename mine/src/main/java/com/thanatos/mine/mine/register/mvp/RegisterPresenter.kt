package com.thanatos.mine.mine.register.mvp

import com.thanatos.baselibrary.RegexUtil
import com.thanatos.baselibrary.ext.isEmpty
import com.thanatos.baselibrary.mvp.BasePresenter

class RegisterPresenter : BasePresenter<RegisterView>() {

    private val registerModel: RegisterModel by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        RegisterModel()
    }


    fun register(account: String, password: String, repassword: String){

        //当前页面不显示不进行数据加载
        if (!visible()) return

        if (account.isEmpty()){
            getView().showSnackBar("账号为空")
            return
        }
        if (RegexUtil.isUserName(account)
                && RegexUtil.isEmail(account)
                && RegexUtil.isMobileSimple(account)){
            getView().showSnackBar("账号不是手机号或邮箱")
            return
        }
        if (password.isEmpty()){
            getView().showSnackBar("密码为空")
            return
        }
        if (repassword.isEmpty()){
            getView().showSnackBar("重复密码为空")
            return
        }
        if (password.length < 6){
            getView().showSnackBar("密码长度不能少于6位")
            return
        }
        if (repassword != password){
            getView().showSnackBar("两次密码不相同")
            return
        }
        if (!RegexUtil.isPassword(password)){
            getView().showSnackBar("密码不能包含特殊字符")
            return
        }

        getView().showProgress("注册中...")

        registerModel.register(account,password,{
            ok, msg ->

            getView().hideProgress()

            if (ok){
                getView().finishRegister()
            }else{
                getView().showSnackBar(msg)
            }

        })
    }
}