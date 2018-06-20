package com.thanatos.wanandroid

import android.os.Bundle
import com.thanatos.baselibrary.data.RemoteData
import com.thanatos.baselibrary.mvp.BaseMvpActivity
import com.thanatos.baselibrary.mvp.BaseView
import pw.androidthanatos.annotation.Path

@Path("/app_main")
class MainActivity : BaseMvpActivity<BaseView,TestPresenter>() {

    override fun getPresenter(): Class<TestPresenter> {
        return TestPresenter::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RemoteData.articleData.getIndexBanner()
    }


}
