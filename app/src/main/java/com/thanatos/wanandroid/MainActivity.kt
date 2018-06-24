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
import kotlinx.android.synthetic.main.activity_main.*
import pw.androidthanatos.annotation.Path
import pw.androidthanatos.router.DispatcherActivity
import pw.androidthanatos.router.Router
import java.util.*

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

        banner.addList(Arrays.asList("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1529842671319&di=2f401e4c5a7ce7fdf2973da0a3480f5d&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2Fd%2F570f4a85c88e6.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1529842693116&di=d6c7ebd5050a1680398bf52d73c197a0&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fsoftbbs%2F1108%2F31%2Fc0%2F8829200_1314778455720_1024x1024soft.jpg",
                "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3883729263,605988563&fm=27&gp=0.jpg"))
    }

    fun a(view: View){

        //Router.getInstance().path("/mine/login")
        val intent = Intent(this,DispatcherActivity::class.java)
        intent.data = Uri.parse("router://thanatos.wanandroid/mine/login?a=1&b=true")
        startActivity(intent)
        //Router.getInstance().uri("router://thanatos.wanandroid/main/login?a=1&b=true")
    }


}
