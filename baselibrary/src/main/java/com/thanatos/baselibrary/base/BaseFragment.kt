package com.thanatos.baselibrary.base

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thanatos.baselibrary.ext.printLog

/**
 * 功能描述: Fragment基类
 * @className: BaseFragment
 * @author: thanatos
 * @createTime: 2018/6/25
 * @updateTime: 2018/6/25 16:28
 */
abstract class BaseFragment : Fragment() {

    private var hasCreate: Boolean = false
    private var noLoad: Boolean = true

    protected var mView: View? = null


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        checkCanInit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hasCreate = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.mView = View.inflate(context,getContentView(),null)
        return this.mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkCanInit()
    }

    private fun checkCanInit(){
        if (hasCreate && userVisibleHint && noLoad){
            noLoad = false
            hasCreate = false
            onInit()
        }
    }

    /**
     * 使用此方法初始化数据
     */
    abstract fun onInit()

    @LayoutRes
    abstract fun getContentView(): Int


    /**
     * 绑定控件
     */
    open fun <V: View> findViewById(@IdRes id: Int): V {
        if (mView == null){
            throw NullPointerException("please set layout ")
        }
        return mView?.findViewById(id) as V
    }



}
