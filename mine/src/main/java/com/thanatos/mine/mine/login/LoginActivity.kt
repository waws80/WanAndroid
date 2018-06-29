package com.thanatos.mine.mine.login

import android.app.Activity
import android.app.ActivityOptions
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.thanatos.baselibrary.ext.setStatusBar
import com.thanatos.baselibrary.mvp.BaseMvpActivity
import com.thanatos.baselibrary.thread.ThreadManager
import com.thanatos.mine.R
import com.thanatos.mine.mine.login.mvp.LoginPresenter
import com.thanatos.mine.mine.login.mvp.LoginView
import kotlinx.android.synthetic.main.mine_activity_login.*
import pw.androidthanatos.annotation.Path
import pw.androidthanatos.router.Request
import pw.androidthanatos.router.Router

/**
 *  功能描述: 登录、注册页面
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/22
 *  @updateTime: 2018/6/22 15:40
 */
@Path("/mine/login")
class LoginActivity : BaseMvpActivity<LoginView, LoginPresenter>(), LoginView {

    override fun getPresenter(): LoginPresenter {
        return LoginPresenter()
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
        val request = Request.Builder(this)
                .path("/mine/register")
                .addOption(ActivityOptions.makeSceneTransitionAnimation(this,btn_login,
                        "btn").toBundle())
                .responseCode(100)
                .resultCallBack { resultCode, data ->
                    if (resultCode == Activity.RESULT_OK){
                        setResult(Activity.RESULT_OK)
                        onBackPressed()
                    }
                }
                .build()
        Router.getInstance().newCall(request).execute()
    }

    //数据回调--------------------------------

    override fun finishLogin() {
        showInfo("登录成功")
        ThreadManager.handler.postDelayed({
            setResult(Activity.RESULT_OK)
            onBackPressed()
        },1000)
    }


}
