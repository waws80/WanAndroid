package com.thanatos.article.index.mvp

import com.thanatos.baselibrary.mvp.BaseModel
import com.thanatos.baselibrary.net.IndexBannerBean

/**
 *  功能描述: 首页model
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/26
 *  @updateTime: 2018/6/26 10:38
 */
class ArticleIndexModel: BaseModel() {

    /**
     * 获取首页轮播图数据
     */
    fun getArticleBanner(next:(Boolean, List<IndexBannerBean>, String) -> Unit) {
        mRemoteData.articleData.getIndexBanner(next)
    }
}