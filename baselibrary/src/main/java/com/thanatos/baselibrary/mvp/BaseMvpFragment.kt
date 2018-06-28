package com.thanatos.baselibrary.mvp

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.NonNull
import android.support.transition.Slide
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.thanatos.baselibrary.base.BaseApplication
import com.thanatos.baselibrary.base.BaseFragment
import com.thanatos.baselibrary.ext.dp2px
import com.thanatos.baselibrary.ext.showSnackBarMsg

/**
 *  功能描述: mvpFragment基类
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/26
 *  @updateTime: 2018/6/26 09:39
 */
abstract class BaseMvpFragment<V: BaseView, P: BasePresenter<V>> : BaseFragment(), BaseView{

    protected var mPresenter: P? = null

    @CallSuper
    override fun onInit(savedInstanceState: Bundle?) {
        mPresenter = PresenterProviders.ofFragment(this).get(getPresenter())
        mPresenter?.attach(this as V)
        mPresenter?.onStart()
    }


    abstract fun getPresenter(): P

    override fun showProgress(@NonNull text: String) {
        showProgressDialog(text)
    }

    override fun hideProgress() {
        hideProgressDialog()
    }

    override fun showInfo(text: String, gravity: Int) {
        toast(text = text,gravity = gravity)
    }

    override fun showSnackBar(msg: String, duration: Int) {
        activity?.showSnackBarMsg(msg,duration)
    }

    /**
     * 日志
     */
    protected open fun log(msg: String, tag: String = "BaseFragment") {
        Log.d(tag, msg)
    }

    /**
     * 弹窗
     */
    protected fun toast(text: String, duration: Int = Toast.LENGTH_SHORT,
                        @Slide.GravityFlag gravity: Int = Gravity.BOTTOM, x: Int = 0, y: Int = activity?.dp2px(60)!!){
        val toast = Toast.makeText(BaseApplication.context,text,duration)
        toast.setGravity(gravity,x,y)
        toast.show()
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.onStart()
    }

    override fun onPause() {
        super.onPause()
        mPresenter?.onPause()
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
    }



}