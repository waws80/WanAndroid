package com.thanatos.baselibrary.widget.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.thanatos.baselibrary.BuildConfig
import com.thanatos.baselibrary.R

/**
 * 功能描述:
 * @className: BaseDialog
 * @author: thanatos
 * @createTime: 2018/6/21
 * @updateTime: 2018/6/21 09:43
 */
class BaseDialog : Dialog {

    //parent view
    private var mView: View? = null

    private var mCustom: Boolean = false

    //attr
    private var mIconDrawable: Drawable? = null
    private var mTitle: CharSequence = ""
    private var mContent: CharSequence = ""
    private var mCancelText: CharSequence = ""
    private var mSubmitText: CharSequence = ""

    //view
    private lateinit var ivIcon: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvContent: TextView
    private lateinit var tvCancel: TextView
    private lateinit var tvSubmit: TextView

    private var mCancelClick: (BaseDialog) -> Unit? = {}
    private var mSubmitClick: (BaseDialog) -> Unit? = {}

    public constructor(context: Context, themeResId: Int = com.thanatos.baseres.R.style.WanAndroidDialogTheme)
            : super(context, themeResId)


    fun setCustomView(@LayoutRes layoutId: Int): BaseDialog{
        setCustomView(View.inflate(context,layoutId,null))
        return this
    }

    fun setCustomView(@NonNull view: View): BaseDialog{
        this.mView = view
        this.mCustom = true
        return this
    }

    fun setIcon(@DrawableRes drawableId: Int): BaseDialog{
        setIcon(context.resources.getDrawable(drawableId))
        return this
    }

    fun setIcon(@NonNull drawable: Drawable): BaseDialog{
        this.mIconDrawable = drawable
        return this
    }

    fun setTitleText(@NonNull title: CharSequence): BaseDialog{
        this.mTitle = title
        return this
    }

    override fun setTitle(titleId: Int) {
        this.mTitle = context.resources.getString(titleId)
    }

    fun setContent(@NonNull content: CharSequence): BaseDialog{
        this.mContent = content
        return this
    }

    fun setCancelButton(@NonNull text: CharSequence = context.resources.getString(R.string.app_cancel),
                        cancel:(BaseDialog) -> Unit?): BaseDialog{
        this.mCancelText = text
        this.mCancelClick = cancel
        return this
    }

    fun setSubmitButton(@NonNull text: CharSequence = context.resources.getString(R.string.app_sure),
                        submit:(BaseDialog) -> Unit?): BaseDialog{
        this.mSubmitText = text
        this.mSubmitClick = submit
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mView == null){
            mView = View.inflate(context, R.layout.base_dialog,null)
        }
        setContentView(mView)

        setCancelable(false)

        setCanceledOnTouchOutside(false)
        //自定义的view不显示dialog布局
        if (!mCustom){
            initUI()
            initData()
            initListener()
        }else{
            if (BuildConfig.APP_DEBUG){
                Log.d("BaseDialog","自定义dialog布局不显示默认的布局")
            }

        }

    }

    private fun initUI(){
        ivIcon = findViewById(R.id.iv_base_dialog_icon)
        tvTitle = findViewById(R.id.tv_base_dialog_title)
        tvContent = findViewById(R.id.tv_base_dialog_content)
        tvCancel = findViewById(R.id.tv_base_dialog_cancel)
        tvSubmit = findViewById(R.id.tv_base_dialog_submit)
    }

    private fun initData(){
        //设置icon
        if (mIconDrawable != null){
            mIconDrawable?.setBounds(0,0,mIconDrawable?.intrinsicWidth!!,mIconDrawable?.intrinsicHeight!!)
            ivIcon.visibility = View.VISIBLE
        }else{
            ivIcon.visibility = View.GONE
        }

        //设置标题
        if (TextUtils.isEmpty(mTitle)){
            if (mIconDrawable == null){
                tvTitle.visibility = View.GONE
            }
        }else{
            tvTitle.text = mTitle
        }

        //设置文本
        if (!TextUtils.isEmpty(mContent)){
            tvContent.text = mContent
        }

        //设置按钮
        if (!TextUtils.isEmpty(mCancelText)){
            tvCancel.text = mCancelText
            tvCancel.isEnabled = true
        }else{
            tvCancel.visibility = View.INVISIBLE
            tvCancel.isEnabled = false
        }

        if (!TextUtils.isEmpty(mSubmitText)){
            tvSubmit.text = mSubmitText
            tvSubmit.isEnabled = true
        }else{
            tvSubmit.visibility = View.INVISIBLE
            tvSubmit.isEnabled = false
        }

        if(TextUtils.isEmpty(mSubmitText) && TextUtils.isEmpty(mCancelText)){
            tvCancel.visibility = View.GONE
            tvSubmit.visibility = View.GONE
        }

    }

    /**
     * 初始化事件
     */
    private fun initListener(){
        //取消按钮点击事件
        tvCancel.setOnClickListener{
            this.dismiss()
            this.mCancelClick.invoke(this)
        }

        //确认按钮点击事件
        tvSubmit.setOnClickListener{
            this.dismiss()
            this.mSubmitClick.invoke(this)
        }

    }

    override fun show() {
        super.show()
        val params = window.attributes
        if (mCustom){
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT
            window.decorView.setPadding(0,0,0,0)
            window.attributes = params
        }
        params.alpha = 1f
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (isShowing){
                dismiss()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }


}
