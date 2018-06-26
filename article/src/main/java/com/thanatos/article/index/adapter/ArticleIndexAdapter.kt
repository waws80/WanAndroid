package com.thanatos.article.index.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.thanatos.article.R

class ArticleIndexAdapter : RecyclerView.Adapter<ArticleIndexAdapter.ArticleIndexViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleIndexViewHolder {
        return ArticleIndexViewHolder(View.inflate(parent.context, R.layout.article_item_index_home, null))
    }

    override fun getItemCount() = 20

    override fun onBindViewHolder(holder: ArticleIndexViewHolder, position: Int) {
    }


    inner class ArticleIndexViewHolder(item: View): RecyclerView.ViewHolder(item)
}