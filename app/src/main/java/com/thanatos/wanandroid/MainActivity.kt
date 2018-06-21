package com.thanatos.wanandroid

import android.os.Bundle
import android.view.View
import com.thanatos.baselibrary.data.RemoteData
import com.thanatos.baselibrary.mvp.BaseMvpActivity
import com.thanatos.baselibrary.mvp.BaseView
import com.thanatos.baselibrary.widget.dialog.BaseDialog
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

    fun a(view: View){
        BaseDialog(this)
                .setTitleText("哈哈")
                .setContent("我是文本内容，哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈")
                .setCancelButton("cancel",  {
                    log("cancel")
                })
                .setSubmitButton("submit",{
                    log("submit")
                })
                .show()

    }


}
