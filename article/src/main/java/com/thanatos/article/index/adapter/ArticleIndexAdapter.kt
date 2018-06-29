package com.thanatos.article.index.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.thanatos.article.R
import com.thanatos.baselibrary.ext.*
import com.thanatos.baselibrary.net.ArticleBean
import kotlinx.android.synthetic.main.article_item_index_home.view.*
import pw.androidthanatos.router.Request
import pw.androidthanatos.router.Router

/**
 *  功能描述: ArticleIndexAdapter
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/27
 *  @updateTime: 2018/6/27 18:23
 */
class ArticleIndexAdapter(private val activity: Activity) : RecyclerView.Adapter<ArticleIndexAdapter.ArticleIndexViewHolder>(){

    val data = mutableListOf<ArticleBean>()

    private var _collect:(Int, Int) -> Unit? = { id: Int, position: Int -> }

    private var _unCollect:(Int, Int) -> Unit? = {id: Int, position: Int ->}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleIndexViewHolder {
        return ArticleIndexViewHolder(View.inflate(parent.context, R.layout.article_item_index_home, null))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ArticleIndexViewHolder, position: Int) {
        val bean = data[position]
        holder.itemView.ivIcon.withCircleRes(com.thanatos.baseres.R.mipmap.ic_launcher_round)
        holder.itemView.tvUserName.text = bean.author
        holder.itemView.tvType.text = bean.chapterName
        holder.itemView.tvContent.text = bean.title
        holder.itemView.rbCollect.isChecked = bean.collect
        holder.itemView.tvDate.text = bean.niceDate

        holder.itemView.rbCollect.setOnClickListener {
            holder.itemView.rbCollect.isChecked = bean.collect
            if (!hasNet()){//当前网络不可用
                toast(NO_NET)
                return@setOnClickListener
            }
            if (!isLogin()){ //没有登录，去登录页面
                Router.getInstance().path("/mine/login")
                return@setOnClickListener
            }
            bean.collect = !bean.collect
            holder.itemView.rbCollect.isChecked = bean.collect
            if (bean.collect){
                this._collect(bean.id,holder.adapterPosition)
            }else{
                this._unCollect(bean.id,holder.adapterPosition)
            }
        }

    }

    inner class ArticleIndexViewHolder(item: View): RecyclerView.ViewHolder(item){
        init {
            item.setOnClickListener {
                val request = Request.Builder(item.context)
                        .path("/article/webContent")
                        .addString("link",data[adapterPosition].link)
                        //.addString("author",data[adapterPosition].author)
                        .addString("title",data[adapterPosition].title)
                        .addOption(ActivityOptions.makeSceneTransitionAnimation(activity,
                                item.tvContent,"title").toBundle())
                        .build()
                Router.getInstance().newCall(request).execute()
            }
        }
    }

    public fun addCollectListener(collect:(Int, Int) -> Unit?, unCollect:(Int, Int) -> Unit?){
        this._collect = collect
        this._unCollect = unCollect
    }
}