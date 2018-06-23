package com.thanatos.wanandroid

import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.view.View
import android.view.Window
import com.thanatos.baselibrary.mvp.BaseMvpActivity
import com.thanatos.baselibrary.mvp.BaseView
import pw.androidthanatos.annotation.Path
import pw.androidthanatos.router.Router

@Path("/app_main")
class MainActivity : BaseMvpActivity<BaseView,TestPresenter>() {

    override fun getPresenter(): Class<TestPresenter> {
        return TestPresenter::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        val fade = Fade()
        fade.duration = 500
        window.enterTransition = fade
        window.exitTransition = fade
        setContentView(R.layout.activity_main)
        //设置导航栏
        setNavigationColor()

        //RemoteData.articleData.getIndexBanner()
    }

    fun a(view: View){

        Router.getInstance().path("/mine/login")
    }


}
