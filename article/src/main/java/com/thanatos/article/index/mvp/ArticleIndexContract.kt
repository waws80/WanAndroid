package com.thanatos.article.index.mvp

import com.thanatos.baselibrary.mvp.BaseView
import com.thanatos.baselibrary.net.ArticleListBean
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

    /**
     * 完成首页文章加载
     */
    fun finishArticle(bean: ArticleListBean)

    /**
     * 收藏文章失败
     */
    fun collectError(position: Int)

    /**
     * 撤销收藏失败
     */
    fun unCollectError(position: Int)

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

    /**
     * 获取首页文章列表
     */
    fun getArticleList(page: Int)

    /**
     * 收藏文章
     */
    fun collectArticle(id: Int, position: Int)

    /**
     * 去掉收藏
     */
    fun unCollectArticle(id: Int, position: Int)
}