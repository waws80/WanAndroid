package com.thanatos.baselibrary.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.*
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.thanatos.baselibrary.R
import com.thanatos.baselibrary.ext.printLog
import kotlinx.android.synthetic.main.base_layout.*

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

    private var mView: View? = null

    private var mBundle: Bundle? = null

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        checkCanInit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBundle = savedInstanceState
        hasCreate = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.mView = View.inflate(context,R.layout.base_layout,null)
        initErrorView()
        return this.mView
    }

    /**
     * 校验用户是否添加了布局
     */
    private fun checkAddView(){
        if (base_content.childCount == 0){
            printLog("please call setContentView()")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBundle = savedInstanceState
        checkCanInit()
    }

    private fun checkCanInit(){
        if (hasCreate && userVisibleHint && noLoad){
            noLoad = false
            hasCreate = false
            onInit(mBundle)
            checkAddView()
        }
    }

    /**
     * 使用此方法初始化数据
     */
    abstract fun onInit(savedInstanceState: Bundle?)

    /**
     * 初始化布局
     */
    protected fun setContentView(@LayoutRes id: Int){
        setContentView(View.inflate(context,id,null))
    }

    protected fun setContentView(@NonNull view: View){
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        base_content.addView(view,params)
    }


    /**
     * 绑定控件
     */
    protected fun <V: View> findViewById(@IdRes id: Int): V {
        if (mView == null){
            throw NullPointerException("please set layout ")
        }
        return mView?.findViewById(id) as V
    }

    /**
     * 显示进度dialog
     */
    protected fun showProgressDialog(@NonNull text: CharSequence = resources.getString(R.string.app_loading)){
        base_progress.visibility = View.VISIBLE
        tv_progress.text = text
    }

    /**
     * 隐藏进度条
     */
    protected fun hideProgressDialog(){
        base_progress.visibility = View.GONE
    }

    /**
     * 显示错误、空布局
     */
    protected fun showErrorLayout(@DrawableRes resourceId: Int = R.drawable.ic_empty_view,
                        text: String = resources.getString(R.string.app_empty)){
        findViewById<View>(R.id.base_empty).visibility = View.VISIBLE
        findViewById<View>(R.id.base_content).visibility = View.GONE
        findViewById<View>(R.id.view_empty_bg).setBackgroundResource(resourceId)
        findViewById<TextView>(R.id.tv_empty_content).text = text
    }

    /**
     * 隐藏错误、空布局
     */
    protected fun hideErrorView(){
        findViewById<View>(R.id.base_empty).visibility = View.GONE
        findViewById<View>(R.id.base_content).visibility = View.VISIBLE
    }

    /**
     * 初始化空view
     */
    private fun initErrorView(){
        findViewById<Toolbar>(R.id.toolbar_empty).visibility = View.GONE
        findViewById<View>(R.id.tv_empty_content).setOnClickListener { onErrorTextViewClick() }
    }

    protected open fun onErrorTextViewClick(){}

    @SuppressLint("WrongViewCast")
    protected fun getErrorTextView(): TextView{
        return findViewById<TextView>(R.id.tv_empty_content)
    }


    @CallSuper
    open fun onBackPressed(): Boolean{
        activity?.onBackPressed()
        return false
    }



}
