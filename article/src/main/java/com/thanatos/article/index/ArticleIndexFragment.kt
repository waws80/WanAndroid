package com.thanatos.article.index

import com.thanatos.article.R
import com.thanatos.baselibrary.base.BaseFragment
import com.thanatos.baselibrary.ext.printLog

/**
 *  功能描述:  文章首页
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/25
 *  @updateTime: 2018/6/25 13:54
 */
class ArticleIndexFragment : BaseFragment(){

    override fun onInit() {
        printLog("首页加载了")
    }

    override fun getContentView() = R.layout.article_fragment_index_home


}