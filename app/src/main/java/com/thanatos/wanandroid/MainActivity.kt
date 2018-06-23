package com.thanatos.wanandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.view.View
import android.view.Window
import com.thanatos.baselibrary.mvp.BaseMvpActivity
import com.thanatos.baselibrary.mvp.BaseView
import pw.androidthanatos.annotation.Path
import pw.androidthanatos.router.DispatcherActivity
import pw.androidthanatos.router.Router

@Path("/app/main")
class MainActivity : BaseMvpActivity<BaseView,TestPresenter>() {

    override fun getPresenter(): TestPresenter {
        return TestPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        //Router.getInstance().path("/mine/login")
        val intent = Intent(this,DispatcherActivity::class.java)
        intent.data = Uri.parse("router://thanatos.wanandroid/mine/login?a=1&b=true")
        startActivity(intent)
        //Router.getInstance().uri("router://thanatos.wanandroid/main/login?a=1&b=true")
    }


}
