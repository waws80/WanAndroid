package com.thanatos.article.index.mvp

import com.thanatos.baselibrary.ext.hasNet
import com.thanatos.baselibrary.mvp.BasePresenter

/**
 *  功能描述: 首页presenter实现类
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/26
 *  @updateTime: 2018/6/26 10:39
 */
class ArticleIndexPresenter: BasePresenter<ArticleIndexView>(),IArticleIndexPresenter {

    private val articleModel: ArticleIndexModel by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
        ArticleIndexModel()
    }

    override fun getBannerInfo() {
        //当前活动页面不存在
        if (!visible()) return
        //当前网络不可用
        if (!hasNet()) {
            getView().showSnackBar("当前网络不可用")
            return
        }
        getView().showProgress()
        articleModel.getArticleBanner({ ok, beans, msg ->
            getView().hideProgress()
            if (ok){
                getView().finishBanner(beans)
            }else{
                getView().showSnackBar(msg)
            }

        })
    }
}