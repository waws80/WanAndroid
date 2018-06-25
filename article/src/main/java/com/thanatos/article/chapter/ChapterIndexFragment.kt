package com.thanatos.article.chapter

import com.thanatos.article.R
import com.thanatos.baselibrary.base.BaseFragment
import com.thanatos.baselibrary.ext.printLog

class ChapterIndexFragment: BaseFragment() {
    override fun onInit() {

        printLog("体系加载了")
    }

    override fun getContentView() = R.layout.article_fragment_index_home


}