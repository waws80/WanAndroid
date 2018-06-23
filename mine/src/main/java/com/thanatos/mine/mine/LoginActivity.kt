package com.thanatos.mine.mine

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.thanatos.baselibrary.ext.setStatusBar
import com.thanatos.baselibrary.mvp.BaseMvpActivity
import com.thanatos.mine.R
import com.thanatos.mine.mine.mvp.LoginPresenter
import com.thanatos.mine.mine.mvp.LoginView
import kotlinx.android.synthetic.main.mine_activity_login.*
import pw.androidthanatos.annotation.Path

/**
 *  功能描述: 登录、注册页面
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/22
 *  @updateTime: 2018/6/22 15:40
 */
@Path("/mine/login")
class LoginActivity : BaseMvpActivity<LoginView,LoginPresenter>(), LoginView {

    override fun getPresenter(): Class<LoginPresenter> {
        return LoginPresenter::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mine_activity_login)

        //设置状态栏
        setStatusBar(color = Color.WHITE,lightMode = true)
        //设置导航栏颜色
        setNavigationColor(resources.getColor(R.color.colorPrimaryDark))

    }


    /**
     * 登录按钮点击事件
     */
    fun click_login(view: View){
        mPresenter?.login(edt_login_account.text.toString(),
                edt_login_password.text.toString())
    }

    /**
     * 忘记密码
     */
    fun click_repassword(view: View){
        showSnackBar("开发中...")
    }

    /**
     * 注册
     */
    fun click_register(view: View){
        showSnackBar("注册")
    }

    //数据回调--------------------------------

    override fun finishLogin() {
        showInfo("登录成功")
    }


}
