package com.thanatos.article.index

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.ImageView
import android.widget.TextView
import com.thanatos.article.R
import com.thanatos.article.index.adapter.ArticleIndexAdapter
import com.thanatos.article.index.mvp.ArticleIndexPresenter
import com.thanatos.article.index.mvp.ArticleIndexView
import com.thanatos.baselibrary.ext.roundRect
import com.thanatos.baselibrary.image.IImageLoader
import com.thanatos.baselibrary.image.ITitleLoader
import com.thanatos.baselibrary.image.ItemClickListener
import com.thanatos.baselibrary.mvp.BaseMvpFragment
import com.thanatos.baselibrary.net.IndexBannerBean
import kotlinx.android.synthetic.main.article_fragment_index_home.*

/**
 *  功能描述:  文章首页
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/25
 *  @updateTime: 2018/6/25 13:54
 */
class ArticleIndexFragment : BaseMvpFragment<ArticleIndexView, ArticleIndexPresenter>(),
    ArticleIndexView{

    override fun getPresenter(): ArticleIndexPresenter {
        return ArticleIndexPresenter()
    }

    override fun onInit(savedInstanceState: Bundle?) {
        super.onInit(savedInstanceState)
        setContentView(R.layout.article_fragment_index_home)
        mPresenter?.getBannerInfo()

        articleRV.layoutManager = GridLayoutManager(activity,1)
        articleRV.adapter = ArticleIndexAdapter()
    }

    override fun finishBanner(data: List<IndexBannerBean>) {
        articleBanner.imageLoader = object : IImageLoader{
            override fun load(imageView: ImageView, data: Any) {
                val bean = data as IndexBannerBean
                imageView.roundRect(bean.imagePath,0)
            }

        }
        articleBanner.itemClickListener = object : ItemClickListener {
            override fun click(position: Int, data: Any) {
                val bean = data as IndexBannerBean
                showSnackBar(bean.url)
            }

        }
        articleBanner.titleLoader = object : ITitleLoader{
            override fun load(titleView: TextView, data: Any) {
                val bean = data as IndexBannerBean
                titleView.text = bean.desc
            }

        }
        articleBanner.addList(data)
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }

}