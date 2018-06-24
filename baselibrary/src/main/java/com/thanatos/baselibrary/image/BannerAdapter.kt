package com.thanatos.baselibrary.image

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.thanatos.baselibrary.R
import com.thanatos.baselibrary.ext.printLog
import com.thanatos.baselibrary.ext.roundRect
import com.thanatos.baselibrary.ext.withRes

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {
    private var mScaleType: ImageView.ScaleType = ImageView.ScaleType.FIT_XY

    private val mData = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        val imageView = ImageView(parent.context)
        imageView.scaleType = mScaleType
        imageView.layoutParams = params
        return BannerViewHolder(imageView)
    }

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val imageView = holder.itemView as ImageView
        val p = position - Int.MAX_VALUE.shr(1)
        var realPosition = p % mData.size
        if (realPosition < 0){
            realPosition += mData.size
        }
        imageView.roundRect((mData[realPosition] as String) )

        imageView.setOnClickListener {
            printLog("点击了图片： $realPosition")
        }
    }

    fun setScaleType(scaleType: ImageView.ScaleType) {
        this.mScaleType = scaleType
    }

    fun addList(list: List<Any>) {
        this.mData.clear()
        this.mData.addAll(list)
        notifyDataSetChanged()
    }

    fun getList(): List<Any> {
        return mData
    }


    inner class BannerViewHolder(item: View): RecyclerView.ViewHolder(item)
}