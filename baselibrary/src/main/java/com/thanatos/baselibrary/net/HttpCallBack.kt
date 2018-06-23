package com.thanatos.baselibrary.net

import com.thanatos.baselibrary.gson.GsonUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class HttpCallBack : Observer<Any>{

    companion object {
        fun getInstance() = HttpCallBack()
    }

    private lateinit var _next: (Any?, ResponseException) -> Unit

    fun callBack(next: (Any?,ResponseException) -> Unit): HttpCallBack{
        _next = next

        return this
    }

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(data: Any) {
        val json = GsonUtil.toJson(data)
        val responseBean = GsonUtil.getGson().fromJson(json,ResponseBean::class.java)
        _next.invoke(responseBean.data,ResponseException(responseBean.errorCode,responseBean.errorMsg))
    }

    override fun onError(e: Throwable) {
        _next.invoke(Any(),ResponseException(-1,e.message!!))
    }

}