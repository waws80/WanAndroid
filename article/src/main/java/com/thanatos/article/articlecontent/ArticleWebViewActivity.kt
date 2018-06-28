package com.thanatos.article.articlecontent

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.thanatos.article.R
import com.thanatos.baselibrary.base.BaseActivity
import com.thanatos.baselibrary.ext.hasNet
import kotlinx.android.synthetic.main.article_activity_article_web_view.*
import pw.androidthanatos.annotation.Module
import pw.androidthanatos.annotation.Path

/**
 *  功能描述: 文章详情页面
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/28
 *  @updateTime: 2018/6/28 12:06
 */
@Module("article")
@Path("/article/webContent")
class ArticleWebViewActivity : BaseActivity() {

    private val mTitle: String? by lazy {
        intent.getStringExtra("title")
    }

    private val mUrl: String? by lazy {
        intent.getStringExtra("link")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_activity_article_web_view)
        setSupportActionBar(webViewToolBar)
        initMenu()
        initData()

    }

    private fun initMenu(){
        webViewToolBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.share ->{

                }
                R.id.collect ->{

                }
                R.id.outSide ->{
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mUrl))
                    startActivity(intent)
                }
            }
             false
        }
    }


    override fun onErrorTextViewClick() {
        super.onErrorTextViewClick()
        initData()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initData(){
        if (!hasNet()){
            showErrorView(text = "当前网络不可用")
            return
        }
        webViewToolBar.setNavigationOnClickListener { onBackPressed() }
        supportActionBar?.title = mTitle
        webView.loadUrl(mUrl)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                hideProgressDialog()
            }
        }
        webView.webChromeClient = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress < 60){
                    showProgressDialog("加载中...")
                }else{
                    hideProgressDialog()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_webview_menu,menu)
        return true
    }


}
