package com.thanatos.baselibrary.image

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.thanatos.baselibrary.R
import com.thanatos.baselibrary.ext.roundRect
import com.thanatos.baselibrary.ext.withRes

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        val imageView = ImageView(parent.context)
        imageView.layoutParams = params
        return BannerViewHolder(imageView)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val imageView = holder.itemView as ImageView
        imageView.withRes(R.mipmap.login_bg)
    }


    inner class BannerViewHolder(item: View): RecyclerView.ViewHolder(item){
        init {
            item.setOnClickListener {

            }
        }
    }
}