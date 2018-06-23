package com.thanatos.mine.mine.mvp

import com.thanatos.baselibrary.RegexUtil
import com.thanatos.baselibrary.ext.isEmpty
import com.thanatos.baselibrary.mvp.BasePresenter

/**
 *  功能描述: 登录presenter
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/22
 *  @updateTime: 2018/6/22 16:01
 */
class LoginPresenter : BasePresenter<LoginView>(), ILoginPresenter{

    //懒加载实例化mnodel
    private val mLoginModel: LoginModel by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        LoginModel()
    }

    override fun login(account: String, password: String) {
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
        if (password.length < 6){
            getView().showSnackBar("密码长度不能少于6位")
            return
        }
        if (!RegexUtil.isPassword(password)){
            getView().showSnackBar("密码不能包含特殊字符")
            return
        }
        getView().showProgress("登录中...")

        //model进行登录操作
        this.mLoginModel.login(account,password,{
            ok, msg ->
            getView().hideProgress()
            if (ok){
                getView().finishLogin()
            }else{
                getView().showSnackBar(msg)
            }
        })
    }
}