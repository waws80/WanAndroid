package com.thanatos.baselibrary.image

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 *  功能描述: 轮播图适配器
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/25
 *  @updateTime: 2018/6/25 10:08
 */
class BannerAdapter : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {
    var scaleType: ImageView.ScaleType = ImageView.ScaleType.FIT_XY

    var data = emptyList<Any>()

    var itemClickListener: ItemClickListener? = null

    var imageLoader: IImageLoader? =null

    var mTitleView: TextView?= null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        val imageView = ImageView(parent.context)
        imageView.scaleType = scaleType
        imageView.layoutParams = params
        return BannerViewHolder(imageView)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val imageView = holder.itemView as ImageView
        val p = position - Int.MAX_VALUE.shr(1)
        if (data.isNotEmpty()){
            var realPosition = p % data.size
            if (realPosition < 0){
                realPosition += data.size
            }
            //加载图片回调
            imageLoader?.load(imageView,data[realPosition])

            //显示标题

            //图片点击事件回调
            imageView.setOnClickListener {
                this.itemClickListener?.click(realPosition,data[realPosition])
            }
        }
    }

    fun setList(list: List<Any>) {
        this.data = list
        notifyDataSetChanged()
    }

    inner class BannerViewHolder(item: View): RecyclerView.ViewHolder(item)
}