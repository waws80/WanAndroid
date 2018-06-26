package com.thanatos.article.index.mvp

import com.thanatos.baselibrary.mvp.BaseView
import com.thanatos.baselibrary.net.IndexBannerBean

/**
 *  功能描述: 首页 view
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/26
 *  @updateTime: 2018/6/26 10:35
 */
interface ArticleIndexView: BaseView{

    /**
     * 完成首页轮播图加载
     */
    fun finishBanner(data: List<IndexBannerBean>)

}

/**
 *  功能描述: 首页presenter
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/26
 *  @updateTime: 2018/6/26 10:37
 */
interface IArticleIndexPresenter{

    /**
     * 获取轮播图数据
     */
    fun getBannerInfo()
}