package com.thanatos.baselibrary.mvp

import android.util.Log

/**
 * 功能描述: presenter基类 并且赋予了和对应 view 相关联的生命周期
 * @className: BasePresenter
 * @author: thanatos
 * @createTime: 2018/6/20
 * @updateTime: 2018/6/20 14:42
 */
abstract class BasePresenter<V : BaseView> : PresenterLifeCallBack<V> {

    /**
     * 与之相关联的view[V]
     */
    protected var mView: V? = null

    /**
     * 是否对用户可见，不可见的时候可能与之对应的view正在创建或者正在销毁
     */
    private var isShowAndCanLoadData = false


    override fun attach(view: V) {
        this.mView = view
        Log.d(TAG, "attach: ")
    }

    override fun onStart() {
        this.isShowAndCanLoadData = true
        Log.d(TAG, "onStart: ")
    }

    /**
     * 当前presenter是否对用户可见
     * @return true: 可见   false： 不可见
     */
    override fun visible(): Boolean {
        return this.isShowAndCanLoadData
    }

    /**
     * 获取当前view 调用此方法必须保证  visible 为真 mView 不为空
     */
    fun getView(): V{
        return mView!!
    }


    override fun onPause() {
        this.isShowAndCanLoadData = false
        Log.d(TAG, "onPause: ")
    }

    override fun onDestroy() {
        this.isShowAndCanLoadData = false
        if (this.mView != null) {
            this.mView = null
        }
        Log.d(TAG, "onDestroy: ")
    }

    companion object {

        private val TAG = "BasePresenter"
    }
}
