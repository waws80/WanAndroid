package com.thanatos.baselibrary.timer

import android.os.CountDownTimer


/**
 *  功能描述:
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/20
 *  @updateTime: 2018/6/20 16:16
 */
class CountDownTimerUtils {

    private var mTotalTime: Long = 60 * 1000

    private var mInterval: Long = 1 * 1000

    private val mOffset = 1000L

    private var canCallBack = true

    private var mCountDownTimer: CountDownTimer? = null

    companion object {
        /**
         * 获取当前类唯一实例对象
         */
         fun getInstance() = CountDownTimerUtils()
    }

    /**
     * 设置总共倒计时事件
     * @param second 秒 默认60秒
     */
    fun setTotalTime(second: Int = 60): CountDownTimerUtils{
        this.mTotalTime = second * mOffset
        return this
    }

    /**
     * 设置间隔
     * @param interval 秒  默认为1秒
     */
    fun setInterval(interval: Int = 1): CountDownTimerUtils{
        this.mInterval = interval * mOffset
        return this
    }

    /**
     * 启动监听
     * @param interval 间隔回调
     * @param finish 倒计时完成回调
     */
    fun start(interval:(Long) -> Unit, finish:() -> Unit){
        if (mCountDownTimer != null){
            mCountDownTimer = null
        }
        mCountDownTimer = object :CountDownTimer(mTotalTime,mInterval){
            override fun onFinish() {
                if (canCallBack){
                    finish.invoke()
                    cancel()
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                if (canCallBack){
                    interval.invoke(millisUntilFinished / mInterval)
                }
            }
        }
        mCountDownTimer?.start()
    }


    /**
     * 取消监听
     */
    fun cancel(){
        if (mCountDownTimer != null){
            mCountDownTimer?.cancel()
            mCountDownTimer = null
        }
    }


    /**
     * 销毁防止内存泄漏
     */
    fun destory(){
        this.canCallBack = false
        cancel()
    }



}
