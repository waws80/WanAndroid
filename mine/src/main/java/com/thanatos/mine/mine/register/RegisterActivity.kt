package com.thanatos.mine.mine.register

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.thanatos.baselibrary.ext.setStatusBar
import com.thanatos.baselibrary.mvp.BaseMvpActivity
import com.thanatos.baselibrary.thread.ThreadManager
import com.thanatos.mine.R
import com.thanatos.mine.mine.register.mvp.RegisterPresenter
import com.thanatos.mine.mine.register.mvp.RegisterView
import kotlinx.android.synthetic.main.mine_activity_register.*
import pw.androidthanatos.annotation.Path

/**
 * 注册模块ui
 */
@Path("/mine/register")
class RegisterActivity : BaseMvpActivity<RegisterView, RegisterPresenter>(), RegisterView {


    override fun getPresenter(): RegisterPresenter {
        return RegisterPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mine_activity_register)
        //设置状态栏
        setStatusBar(Color.WHITE,true)
        //设置导航栏颜色
        setNavigationColor(resources.getColor(R.color.colorPrimaryDark))
    }

    /**
     * 注册按钮点击时间
     */
    fun click_register(view: View){
        mPresenter?.register(edt_register_account.text.toString(),
                edt_register_password.text.toString(),
                edt_register_repassword.text.toString())
    }

    override fun finishRegister() {
        showSnackBar("注册成功")
        ThreadManager.handler.postDelayed({
            setResult(Activity.RESULT_OK)
            onBackPressed()
        },1000)
    }
}
