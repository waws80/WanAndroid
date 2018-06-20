package com.thanatos.baselibrary.net

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

//文章service
interface ArticleService {

    //首页---------------------------------

    //首页轮播图
    @GET("banner/json")
    fun getIndexBanner(): Observable<Any>

}