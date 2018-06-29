package com.thanatos.baselibrary.widget.labelview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 *  功能描述: 标签布局
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/29
 *  @updateTime: 2018/6/29 16:53
 */
class LabelLayout : ViewGroup{


    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context,attrs,-1)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int)
            : super(context, attrs, defStyle){

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        (childCount == 0)?: return
        val realW = measuredWidth - paddingLeft - paddingRight

        var realH = paddingTop
        var currentRowH = 0
        var currentW = 0
        for (index in 0 until childCount){
            val child = getChildAt(index)
            if (child.visibility != View.GONE){
                val childParams: LayoutParams = child.layoutParams as LayoutParams

                val childW = childParams.width + childParams.leftMargin + childParams.rightMargin
                val childH = childParams.height + childParams.topMargin + childParams.bottomMargin
                if (currentW + childW > realW){ //换行
                    realH += currentRowH
                    currentRowH = 0
                    currentW = 0
                }
                val left = paddingLeft + currentW
                val top = realH
                val right = left + childW
                val bottom = top + childH
                child.layout(left,top,right, bottom)
                currentRowH = Math.max(currentRowH,childH)
                currentW += childW
            }

        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var result = 0
        val hModel = MeasureSpec.getMode(heightMeasureSpec)
        val hSize = MeasureSpec.getSize(heightMeasureSpec)
        if (hModel == MeasureSpec.EXACTLY){//确切的尺寸
            result = hSize
        }else if (hModel == MeasureSpec.UNSPECIFIED){
            result = hSize
        }else{
            //获取view占用的高度
            result = getViewRequestHeight()
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), result)
    }


    private fun getViewRequestHeight(): Int{
        (childCount == 0)?: return paddingTop + paddingBottom
        val realW = measuredWidth - paddingLeft - paddingRight

        var realH = paddingTop + paddingBottom
        var currentRowH = 0
        var currentW = 0
        for (index in 0 until childCount){
            val child = getChildAt(index)
           if (child.visibility != View.GONE){
               val childParams: LayoutParams = child.layoutParams as LayoutParams

               val childW = childParams.width + childParams.leftMargin + childParams.rightMargin
               val childH = childParams.height + childParams.topMargin + childParams.bottomMargin
               if (currentW + childW > realW){ //换行
                   realH += currentRowH
                   currentRowH = 0
                   currentW = 0
               }
               currentRowH = Math.max(currentRowH,childH)
               currentW += childW
           }
        }
        realH += currentRowH
        return realH
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return LayoutParams(context, attrs)
    }


    inner class LayoutParams: MarginLayoutParams{

        constructor(context: Context, attrs: AttributeSet?): super(context,attrs)
        constructor(width: Int, height: Int): super(width, height)
        constructor(layoutParams: ViewGroup.LayoutParams?): super(layoutParams)
    }

}