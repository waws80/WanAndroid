package com.thanatos.article.index

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuInflater
import android.view.View
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
import com.thanatos.baselibrary.net.ArticleListBean
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

    private val mAdapter: ArticleIndexAdapter by lazy {
        ArticleIndexAdapter(activity!!)
    }

    private val mActivity: AppCompatActivity by lazy {
        activity as AppCompatActivity
    }

    private var mPage: Int = 0

    override fun getPresenter(): ArticleIndexPresenter {
        return ArticleIndexPresenter()
    }

    override fun onInit(savedInstanceState: Bundle?) {
        super.onInit(savedInstanceState)
        setContentView(R.layout.article_fragment_index_home)
        //默认隐藏banner，获取导数据后开启
        articleBanner.visibility = View.GONE

        //设置toolbar
        articleToolBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.menu_search ->{

                }
                R.id.menu_hot ->{

                }
            }
            false
        }
        mActivity.setSupportActionBar(articleToolBar)
        setHasOptionsMenu(true)

        //开启数据获取
        mPresenter?.getBannerInfo()
        mPresenter?.getArticleList(mPage)

        articleRV.layoutManager = GridLayoutManager(activity,1)
        articleRV.adapter = mAdapter
        initAdapterCollect()
    }

    private fun initAdapterCollect(){
        mAdapter.addCollectListener(collect = {id, position ->
            //去收藏
            mPresenter?.collectArticle(id, position)
        }, unCollect= {id, position ->
            //去掉收藏
            mPresenter?.unCollectArticle(id, position)
        })
    }

    override fun finishBanner(data: List<IndexBannerBean>) {
        articleBanner.visibility = View.VISIBLE
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


    override fun finishArticle(bean: ArticleListBean) {
        if (bean.datas.isEmpty()){
            return
        }
        mPage = bean.curPage+1
        mAdapter.data.addAll(bean.datas)
        mAdapter.notifyDataSetChanged()
    }


    override fun collectError(position: Int) {
        mAdapter.data[position].collect = false
        mAdapter.notifyDataSetChanged()
    }

    override fun unCollectError(position: Int) {
        mAdapter.data[position].collect = true
        mAdapter.notifyDataSetChanged()
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.article_home_menu, menu)
    }

}