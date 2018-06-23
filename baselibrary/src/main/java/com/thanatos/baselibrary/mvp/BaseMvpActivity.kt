package com.thanatos.baselibrary.mvp

import android.os.Bundle
import android.support.annotation.NonNull
import android.support.design.widget.Snackbar
import android.support.transition.Slide
import com.thanatos.baselibrary.base.BaseActivity
import com.thanatos.baselibrary.ext.showSnackBarMsg
import java.time.Duration

/**
 *  功能描述: mvp-activity基类
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/20
 *  @updateTime: 2018/6/20 15:14
 */
abstract class BaseMvpActivity<V: BaseView, P: BasePresenter<V>> : BaseActivity() , BaseView{

    protected var mPresenter: P? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = PresenterProviders.ofActivity(this)
                .get(getPresenter())
        super.onCreate(savedInstanceState)
        log("onCreate")

    }

    abstract fun getPresenter() : P

    override fun showProgress(@NonNull text: String) {
        showProgressDialog(text)
    }

    override fun hideProgress() {
        hideProgressDialog()
    }


    override fun showInfo(@NonNull text: String, @Slide.GravityFlag gravity: Int) {
        toast(text = text,gravity = gravity)
    }

    override fun showSnackBar(msg: String, duration: Int) {
        showSnackBarMsg(msg,duration)
    }


    override fun log(msg: String, tag: String) {
        super.log(msg, "BaseMvpActivity")

    }

    override fun onPause() {
        super.onPause()
        log("onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }





}
