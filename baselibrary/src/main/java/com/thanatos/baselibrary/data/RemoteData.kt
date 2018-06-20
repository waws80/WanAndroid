package com.thanatos.baselibrary.data

import com.thanatos.baselibrary.gson.GsonUtil
import com.thanatos.baselibrary.net.ArticleService
import com.thanatos.baselibrary.net.HttpCallBack
import com.thanatos.baselibrary.net.HttpManager
import com.thanatos.baselibrary.net.IndexBannerBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


object RemoteData{

    val articleData = ArticleData()

    abstract class AbsData{

        open fun apiObservable(observable: Observable<Any>): Observable<Any> =
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    }

    class ArticleData: AbsData(){

        private val mServicer = HttpManager.getRetrofit().create(ArticleService::class.java)

        fun getIndexBanner(){

             apiObservable(mServicer.getIndexBanner())
                     .subscribe(HttpCallBack.getInstance().callBack({
                         data, res ->
                         val list = GsonUtil.fromList(data,IndexBannerBean::class.java)
                         print(list)
                     }))




        }

    }
}
