package com.thanatos.wanandroid

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import com.thanatos.baselibrary.base.BaseActivity
import com.thanatos.baselibrary.mvp.BaseMvpActivity
import com.thanatos.baselibrary.mvp.BaseView
import kotlinx.android.synthetic.main.activity_main.*
import pw.androidthanatos.annotation.Path

@Path("/app_main")
class MainActivity : BaseMvpActivity<BaseView,TestPresenter>() {
    override fun getPresenter(): Class<TestPresenter> {
        return TestPresenter::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun a(view: View){
        showErrorView()
    }

}
